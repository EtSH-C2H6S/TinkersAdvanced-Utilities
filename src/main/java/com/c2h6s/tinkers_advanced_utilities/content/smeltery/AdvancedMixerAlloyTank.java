package com.c2h6s.tinkers_advanced_utilities.content.smeltery;

import com.c2h6s.tinkers_advanced_utilities.content.block.blockEntity.AdvancedAlloyerBlockEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.minecraft.core.Direction;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.recipe.alloying.IMutableAlloyTank;
import slimeknights.tconstruct.smeltery.block.entity.component.DuctBlockEntity;
import slimeknights.tconstruct.smeltery.block.entity.component.SmelteryInputOutputBlockEntity;
import slimeknights.tconstruct.smeltery.block.entity.controller.HeatingStructureBlockEntity;
import slimeknights.tconstruct.smeltery.block.entity.module.alloying.SmelteryAlloyTank;

import java.util.function.Predicate;

@RequiredArgsConstructor
public class AdvancedMixerAlloyTank implements IMutableAlloyTank {
    @Getter
    @Setter
    private int temperature = 0;
    private final AdvancedAlloyerBlockEntity blockEntity;
    private final IFluidHandler outputTank;

    private boolean shouldUpdate = true;

    @Nullable
    private SmelteryAlloyTank smelteryAlloyTank;
    @Nullable
    private Predicate<FluidStack> fluidFilter;

    @Override
    public FluidStack drain(int tank, FluidStack fluidStack) {
        this.findSmeltery();
        return smelteryAlloyTank!=null?smelteryAlloyTank.drain(tank,fluidStack):FluidStack.EMPTY;
    }

    @Override
    public int getTanks() {
        this.findSmeltery();
        return this.smelteryAlloyTank!=null?this.smelteryAlloyTank.getTanks():0;
    }

    @Override
    public FluidStack getFluidInTank(int tank) {
        this.findSmeltery();
        return this.smelteryAlloyTank!=null?this.smelteryAlloyTank.getFluidInTank(tank):FluidStack.EMPTY;
    }

    @Override
    public boolean canFit(FluidStack fluid, int removed) {
        findSmeltery();
        if (this.fluidFilter!=null&&!this.fluidFilter.test(fluid)) return false;
        return outputTank.fill(fluid, IFluidHandler.FluidAction.SIMULATE) == fluid.getAmount();
    }

    @Override
    public int fill(FluidStack fluidStack) {
        return outputTank.fill(fluidStack, IFluidHandler.FluidAction.EXECUTE);
    }

    protected void findSmeltery(){
        if (shouldUpdate) {
            var pos = blockEntity.getBlockPos();
            var level = blockEntity.getLevel();
            if (level == null) return;
            for (Direction direction : Direction.values()) {
                if (direction == Direction.DOWN) continue;
                var fetchPos = pos.relative(direction);
                var be = level.getBlockEntity(fetchPos);
                if (be instanceof SmelteryInputOutputBlockEntity.SmelteryFluidIO fluidIO) {
                    var masterPos = fluidIO.getMasterPos();
                    if (masterPos != null && level.getBlockEntity(masterPos) instanceof HeatingStructureBlockEntity heating) {
                        this.smelteryAlloyTank = new SmelteryAlloyTank(heating.getTank());
                        if (fluidIO instanceof DuctBlockEntity duct)
                            this.fluidFilter = fluid -> duct.getItemHandler().getFluid().isFluidEqual(fluid);
                        break;
                    }
                }
            }
            shouldUpdate = false;
        }
    }

    public void checkUpdate(Direction direction){
        if (direction == Direction.DOWN) {
            return;
        }
        this.shouldUpdate = true;
        this.smelteryAlloyTank = null;
    }
}
