package com.c2h6s.tinkers_advanced_utilities.content.block.blockEntity;

import com.c2h6s.etstlib.register.EtSTLibToolStat;
import com.c2h6s.tinkers_advanced_utilities.api.interfaces.IFuelEngravingModifier;
import com.c2h6s.tinkers_advanced_utilities.api.interfaces.IFuelLensItem;
import com.c2h6s.tinkers_advanced_utilities.content.tool.modifiers.FuelEngraved;
import com.c2h6s.tinkers_advanced_utilities.data.TiAcUTagkeys;
import com.c2h6s.tinkers_advanced_utilities.init.TiAcUBlockEntities;
import com.c2h6s.tinkers_advanced_utilities.init.TiAcUBlocks;
import com.c2h6s.tinkers_advanced_utilities.init.TiAcUModifiers;
import com.c2h6s.tinkers_advanced_utilities.mixin.tconMixin.TinkerStationContainerWrapperAccessor;
import com.c2h6s.tinkers_advanced_utilities.utils.CommonUUtils;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.ModelData;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.wrapper.SidedInvWrapper;
import slimeknights.mantle.fluid.FluidTransferHelper;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.common.SoundUtils;
import slimeknights.tconstruct.fluids.TinkerFluids;
import slimeknights.tconstruct.library.client.model.ModelProperties;
import slimeknights.tconstruct.library.fluid.FluidTankAnimated;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.ModifierId;
import slimeknights.tconstruct.library.modifiers.ModifierManager;
import slimeknights.tconstruct.library.recipe.fuel.MeltingFuelLookup;
import slimeknights.tconstruct.library.tools.item.IModifiable;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;
import slimeknights.tconstruct.library.utils.NBTTags;
import slimeknights.tconstruct.shared.TinkerCommons;
import slimeknights.tconstruct.shared.block.entity.TableBlockEntity;
import slimeknights.tconstruct.shared.particle.FluidParticleData;
import slimeknights.tconstruct.smeltery.block.CastingTankBlock;
import slimeknights.tconstruct.smeltery.block.entity.ITankBlockEntity;
import slimeknights.tconstruct.smeltery.block.entity.component.TankBlockEntity;
import slimeknights.tconstruct.smeltery.item.TankItem;
import slimeknights.tconstruct.tables.block.entity.inventory.TinkerStationContainerWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

import static com.c2h6s.tinkers_advanced_utilities.TiAcUConfig.COMMON;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class FuelEngraverBlockEntity extends TableBlockEntity implements ITankBlockEntity.ITankInventoryBlockEntity, WorldlyContainer {
    @SubscribeEvent
    public static void onItemCrafted(PlayerEvent.ItemCraftedEvent event){
        if (event.getEntity().level().isClientSide) return;
        var stack = event.getCrafting();
        if (stack.getItem() instanceof IModifiable){
            ToolStack toolStack = ToolStack.from(stack);
            var lvl = toolStack.getModifierLevel(TiAcUModifiers.WATER_WASHED.getId());
            if (lvl>0){
                toolStack.removeModifier(TiAcUModifiers.WATER_WASHED.getId(), lvl);
            }
        }
        var player = event.getEntity();
        if (event.getInventory() instanceof TinkerStationContainerWrapper wrapper){
            var station = ((TinkerStationContainerWrapperAccessor)wrapper).tiacu$getStation();
            var level = station.getLevel();
            if (level!=null&&level.getBlockEntity(station.getBlockPos().above(2)) instanceof FuelEngraverBlockEntity engraverBE){
                var fluidStack = engraverBE.getTank().getFluid();
                if (engraverBE.processCraftingResult(event.getCrafting(),player)) engraverBE.craftSoundAndParts(event.getEntity(),fluidStack);
            }
        }
    }

    public boolean processCraftingResult(ItemStack stack,Player player){
        if (!(stack.getItem() instanceof IModifiable)||stack.getItem() instanceof IFuelLensItem) return false;
        var lensStack = getItem(INPUT);
        if (!(lensStack.getItem() instanceof IFuelLensItem fuelLens)||!(lensStack.getItem() instanceof IModifiable)) return false;
        if (!fuelLens.getItemPredicate().test(stack)) return false;
        var fuel = getTank().getFluid();
        var lensTool = ToolStack.from(lensStack);
        var craftedTool = ToolStack.from(stack);
        return fuelLens.processToolResult(craftedTool,lensTool,fuelLens,player,fuel,this);
    }


    public void craftSoundAndParts(Player player,FluidStack fluidStack){
        var option = new FluidParticleData(TinkerCommons.fluidParticle.get(), fluidStack);
        if (getLevel() instanceof ServerLevel serverLevel){
            var pos = getBlockPos().below().getCenter();
            serverLevel.sendParticles(option,pos.x,pos.y,pos.z,20,0.5,0.5,0.5,0.05);
        }
        if (player==null) return;
        Optional.ofNullable(fluidStack.getFluid().getFluidType().getSound(SoundActions.BUCKET_EMPTY))
                .ifPresent(soundEvent ->
                        SoundUtils.playSoundForAll(player, soundEvent, 0.4f, 0.9f + 0.1f * player.getRandom().nextFloat())
        );
    }



    public static int CFG_CAPACITY = 4000;
    public static int CFG_TEMP_EACH_MODIFIER = 750;
    public static int CFG_AMOUNT_EACH_LEVEL = 100;
    public static int CFG_AMOUNT_REMOVE_ENGRAVE = 10;

    public static final int INPUT = 0;
    private static final Component NAME = TConstruct.makeTranslation("gui", "casting");

    @Getter
    protected final FluidTankAnimated tank;
    private final LazyOptional<IFluidHandler> fluidHolder;
    @Getter @Setter
    private int lastStrength = -1;

    public static int getCapacity(Block block) {
        return CFG_CAPACITY;
    }

    public FuelEngraverBlockEntity(BlockPos pos, BlockState state) {
        this(pos, state, state.getBlock() instanceof TankBlockEntity.ITankBlock tankBlock
                ? tankBlock
                : TiAcUBlocks.FUEL_ENGRAVER.get());
    }

    public FuelEngraverBlockEntity(BlockPos pos, BlockState state, TankBlockEntity.ITankBlock block) {
        this(TiAcUBlockEntities.FUEL_ENGRAVER.get(), pos, state, block);
    }

    protected FuelEngraverBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, TankBlockEntity.ITankBlock block) {
        super(type, pos, state, NAME, 2, 1);
        CFG_CAPACITY = COMMON.FUEL_ENGRAVER_CAPACITY.get();
        CFG_AMOUNT_EACH_LEVEL = COMMON.FUEL_ENGRAVER_AMOUNT_EACH_LEVEL.get();
        CFG_TEMP_EACH_MODIFIER = COMMON.FUEL_ENGRAVER_TEMP_EACH_MODIFIER.get();
        CFG_AMOUNT_REMOVE_ENGRAVE = COMMON.FUEL_ENGRAVER_AMOUNT_REMOVE_ENGRAVE.get();
        tank = new FluidTankAnimated(block.getCapacity(), this);
        fluidHolder = LazyOptional.of(() -> tank);
        itemHandler = new SidedInvWrapper(this, Direction.DOWN);
    }

    public void interact(Player player, InteractionHand hand, boolean clickedTank) {
        if (level == null || level.isClientSide) {
            return;
        }
        if (clickedTank) {
            if (!FluidTransferHelper.interactWithContainer(level, worldPosition, tank, player, hand).didTransfer()) {
                FluidTransferHelper.interactWithFilledBucket(level, worldPosition, tank, player, hand, getBlockState().getValue(CastingTankBlock.FACING));
            }
        } else {
            ItemStack input = getItem(INPUT);
            ItemStack held = player.getItemInHand(hand);
            if (!input.isEmpty()) {
                setItem(INPUT, ItemStack.EMPTY);
                ItemHandlerHelper.giveItemToPlayer(player, input, player.getInventory().selected);
            } else if (!held.isEmpty() && canPlaceItem(INPUT, held)) {
                setItem(INPUT, held.split(1));
            }
        }
    }

    private void setInputItem(ItemStack stack) {
        super.setItem(INPUT, stack);
    }

    @Override
    public boolean canPlaceItem(int pIndex, ItemStack pStack) {
        if (pIndex == INPUT) {
            return getItem(INPUT).isEmpty() && !pStack.isEmpty() && pStack.getItem() instanceof IFuelLensItem;
        }
        return false;
    }


    @Override
    @Nonnull
    public int[] getSlotsForFace(Direction side) {
        return new int[]{INPUT};
    }
    @Override
    public boolean canPlaceItemThroughFace(int index, ItemStack itemStackIn, @Nullable Direction direction) {
        return index == INPUT;
    }
    @Override
    public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction) {
        return false;
    }


    @Override
    @Nonnull
    public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction facing) {
        if (capability == ForgeCapabilities.FLUID_HANDLER) {
            return fluidHolder.cast();
        }
        return super.getCapability(capability, facing);
    }
    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        fluidHolder.invalidate();
    }
    @Nonnull
    @Override
    public ModelData getModelData() {
        return ModelData.builder()
                .with(ModelProperties.FLUID_STACK, tank.getFluid())
                .with(ModelProperties.TANK_CAPACITY, tank.getCapacity()).build();
    }
    @Override
    public void onTankContentsChanged() {
        ITankInventoryBlockEntity.super.onTankContentsChanged();
        if (this.level != null) {
            TankBlockEntity.updateLight(this, tank);
            this.requestModelDataUpdate();
        }
    }


    private static final String TAG_REDSTONE = "redstone";

    public void setTankTag(ItemStack stack) {
        TankItem.setTank(stack, tank);
    }

    public void updateTank(CompoundTag nbt) {
        if (nbt.isEmpty()) {
            tank.setFluid(FluidStack.EMPTY);
        } else {
            tank.readFromNBT(nbt);
            TankBlockEntity.updateLight(this, tank);
        }
    }

    @Override
    public void load(CompoundTag tag) {
        tank.setCapacity(getCapacity(getBlockState().getBlock()));
        updateTank(tag.getCompound(NBTTags.TANK));
        super.load(tag);
    }

    @Override
    public void saveSynced(CompoundTag tag) {
        super.saveSynced(tag);
        if (!tank.isEmpty()) {
            tag.put(NBTTags.TANK, tank.writeToNBT(new CompoundTag()));
        }
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return null;
    }
}
