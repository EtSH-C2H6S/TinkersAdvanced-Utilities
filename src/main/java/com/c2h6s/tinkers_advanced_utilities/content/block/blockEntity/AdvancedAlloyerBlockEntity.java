package com.c2h6s.tinkers_advanced_utilities.content.block.blockEntity;

import com.c2h6s.tinkers_advanced_utilities.TiAcUConfig;
import com.c2h6s.tinkers_advanced_utilities.content.smeltery.AdvancedMixerAlloyTank;
import com.c2h6s.tinkers_advanced_utilities.init.TiAcUBlockEntities;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.IFluidHandler;
import slimeknights.mantle.block.entity.MantleBlockEntity;
import slimeknights.mantle.util.BlockEntityHelper;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.fluid.FluidTankAnimated;
import slimeknights.tconstruct.library.utils.NBTTags;
import slimeknights.tconstruct.smeltery.block.controller.ControllerBlock;
import slimeknights.tconstruct.smeltery.block.controller.MelterBlock;
import slimeknights.tconstruct.smeltery.block.entity.ITankBlockEntity;
import slimeknights.tconstruct.smeltery.block.entity.module.SolidFuelModule;
import slimeknights.tconstruct.smeltery.block.entity.module.alloying.SingleAlloyingModule;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class AdvancedAlloyerBlockEntity extends MantleBlockEntity implements ITankBlockEntity {
    public static final BlockEntityTicker<AdvancedAlloyerBlockEntity> SERVER_TICKER = (level, pos, state, self) -> self.tick(level, pos, state);

    protected static int CFG_TANK_CAPACITY = 16000;
    protected static int CFG_ALLOYING_CYCLE = 4;

    @Getter
    protected final FluidTankAnimated tank;
    private final LazyOptional<IFluidHandler> tankHolder;


    @Getter
    private final AdvancedMixerAlloyTank alloyTank;
    private final SingleAlloyingModule alloyingModule;
    @Getter
    private final SolidFuelModule fuelModule;

    @Getter @Setter
    private int lastStrength = -1;

    private int tick;

    public AdvancedAlloyerBlockEntity(BlockPos pos, BlockState state) {
        this(TiAcUBlockEntities.ADVANCED_ALLOYER.get(), pos, state);
    }

    protected AdvancedAlloyerBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        CFG_TANK_CAPACITY = TiAcUConfig.COMMON.ADVANCED_ALLOYER_CAPACITY.get();
        CFG_ALLOYING_CYCLE = TiAcUConfig.COMMON.ADVANCED_ALLOYER_WORK_CYCLE.get();
        this.fuelModule = new SolidFuelModule(this, pos.below());
        this.tank = new FluidTankAnimated(CFG_TANK_CAPACITY, this);
        this.tankHolder = LazyOptional.of(() -> tank);
        this.alloyTank = new AdvancedMixerAlloyTank(this, tank);
        this.alloyingModule = new SingleAlloyingModule(this, alloyTank);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction facing) {
        if (capability == ForgeCapabilities.FLUID_HANDLER) {
            return tankHolder.cast();
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        this.tankHolder.invalidate();
    }

    private boolean isFormed() {
        BlockState state = this.getBlockState();
        return state.hasProperty(MelterBlock.IN_STRUCTURE) && state.getValue(MelterBlock.IN_STRUCTURE);
    }

    private void tick(Level level, BlockPos pos, BlockState state) {
        if (isFormed()) {
            switch (tick) {
                case 0 -> {
                    alloyTank.setTemperature(fuelModule.findFuel(false));
                    if (!fuelModule.hasFuel() && alloyingModule.canAlloy()) {
                        fuelModule.findFuel(true);
                    }
                }
                case 1 -> {
                    boolean hasFuel = fuelModule.hasFuel();
                    if (state.getValue(ControllerBlock.ACTIVE) != hasFuel) {
                        level.setBlockAndUpdate(pos, state.setValue(ControllerBlock.ACTIVE, hasFuel));
                        BlockPos down = pos.below();
                        BlockState downState = level.getBlockState(down);
                        if (downState.is(TinkerTags.Blocks.FUEL_TANKS) && downState.hasProperty(ControllerBlock.ACTIVE) && downState.getValue(ControllerBlock.ACTIVE) != hasFuel) {
                            level.setBlockAndUpdate(down, downState.setValue(ControllerBlock.ACTIVE, hasFuel));
                        }
                    }
                    if (hasFuel) {
                        alloyTank.setTemperature(fuelModule.getTemperature());
                        alloyingModule.doAlloy();
                        fuelModule.decreaseFuel(1);
                    }
                }
            }
        } else if (tick == 1 && fuelModule.hasFuel()) {
            fuelModule.decreaseFuel(1);
        }
        tick = (tick + 1) % CFG_ALLOYING_CYCLE;
    }

    public void neighborChanged(Direction side) {
        alloyTank.checkUpdate(side);
    }

    @Override
    protected boolean shouldSyncOnUpdate() {
        return true;
    }

    @Override
    public void saveSynced(CompoundTag tag) {
        super.saveSynced(tag);
        tag.put(NBTTags.TANK, tank.writeToNBT(new CompoundTag()));
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        fuelModule.writeToTag(tag);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        tank.readFromNBT(nbt.getCompound(NBTTags.TANK));
        fuelModule.readFromTag(nbt);
    }

    @Nullable
    public static <CAST extends AdvancedAlloyerBlockEntity, RET extends BlockEntity> BlockEntityTicker<RET> getTicker(Level level, BlockEntityType<RET> check, BlockEntityType<CAST> casting) {
        if (level.isClientSide) return null;
        return BlockEntityHelper.castTicker(check, casting, SERVER_TICKER);
    }
}