package com.c2h6s.tinkers_advanced_utilities.api.interfaces;

import com.c2h6s.etstlib.register.EtSTLibToolStat;
import com.c2h6s.tinkers_advanced_utilities.content.block.blockEntity.FuelEngraverBlockEntity;
import com.c2h6s.tinkers_advanced_utilities.content.tool.modifiers.FuelEngraved;
import com.c2h6s.tinkers_advanced_utilities.data.TiAcUTagkeys;
import com.c2h6s.tinkers_advanced_utilities.init.TiAcUModifiers;
import com.c2h6s.tinkers_advanced_utilities.utils.CommonUUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.fluids.TinkerFluids;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.ModifierId;
import slimeknights.tconstruct.library.modifiers.ModifierManager;
import slimeknights.tconstruct.library.recipe.fuel.MeltingFuelLookup;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

import static com.c2h6s.tinkers_advanced_utilities.content.block.blockEntity.FuelEngraverBlockEntity.*;

public interface IFuelLensItem {
    Predicate<ItemStack> getItemPredicate();
    default boolean clearEngraved(ToolStack result, ToolStack lens,IFuelLensItem fuelLens, Player player, FluidStack fuel, IFluidHandler handler){
        ModifierId toRemove = result.getModifierList().stream()
                .filter(entry -> entry.getModifier() instanceof IFuelEngravingModifier)
                .map(ModifierEntry::getId).findAny().orElse(null);
        if (toRemove==null) return false;
        int amountNeeded = CommonUUtils.processConsumptionInt(CFG_AMOUNT_REMOVE_ENGRAVE,
                lens.getStats().get(EtSTLibToolStat.FLUID_EFFICIENCY));
        var drained =handler.drain(amountNeeded, IFluidHandler.FluidAction.SIMULATE).getAmount();
        if (drained<amountNeeded) return false;
        result.removeModifier(toRemove,1);
        handler.drain(amountNeeded, IFluidHandler.FluidAction.EXECUTE);
        return true;
    }
    default boolean clearEngraved(ToolStack result, ToolStack lens,IFuelLensItem fuelLens, Player player, FluidStack fuel, FuelEngraverBlockEntity blockEntity){
        return this.clearEngraved(result,lens,fuelLens,player,fuel,blockEntity.getTank());
    }

    default boolean processToolResult(ToolStack result, ToolStack lens,IFuelLensItem fuelLens, Player player, FluidStack fuel,IFluidHandler handler){
        if (fuel.getFluid().isSame(TinkerFluids.venom.get())) return fuelLens.clearEngraved(result,lens,fuelLens,player,fuel,handler);

        if (result.getModifierList().stream().map(ModifierEntry::getModifier)
                .anyMatch(modifier -> modifier instanceof IFuelEngravingModifier)) return false;
        var meltingFuel = MeltingFuelLookup.findFuel(fuel.getFluid());
        if (meltingFuel==null) return false;
        var tempPresent = meltingFuel.getTemperature();
        var fluidConsumption = getBasicFuelConsumption(lens,fuelLens,fuel);

        var tempRequirement = getTempRequirement(lens,fuelLens,fuel);
        if (tempRequirement>tempPresent){
            player.displayClientMessage(Component.translatable("message.tinkers_advanced.engrave_fail_with_low_temp"),true);
            return false;
        }

        float efficiency = lens.getStats().get(EtSTLibToolStat.FLUID_EFFICIENCY);
        fluidConsumption = CommonUUtils.processConsumptionInt(fluidConsumption,efficiency);
        if (handler.drain(fluidConsumption, IFluidHandler.FluidAction.SIMULATE).getAmount()<fluidConsumption){
            player.displayClientMessage(Component.translatable("message.tinkers_advanced.engrave_fail_with_low_fuel"),true);
            return false;
        }

        var modifiersToAdd = lens.getModifierList().stream()
                .filter(entry -> !ModifierManager.isInTag(entry.getId(), TiAcUTagkeys.Modifiers.ENGRAVER_BLACKLIST))
                .toList();
        if (modifiersToAdd.isEmpty()) return false;

        var toolCopy = result.copy();
        toolCopy.getPersistentData().put(FuelEngraved.KEY_EXTRA_MODIFIERS,
                ModifierNBT.builder().add(modifiersToAdd).build().serializeToNBT());
        toolCopy.addModifier(TiAcUModifiers.FUEL_ENGRAVED.getId(),1);
        for (ModifierEntry entry:toolCopy.getModifierList()){
            var c = entry.getHook(ModifierHooks.VALIDATE).validate(toolCopy,entry);
            if (c!=null){
                player.displayClientMessage(Component.
                        translatable("message.tinkers_advanced.engrave_fail_with_modifiers").append(c),true);
                return false;
            }
        }

        handler.drain(CommonUUtils.processConsumptionInt(fluidConsumption,efficiency), IFluidHandler.FluidAction.EXECUTE);
        result.getPersistentData().put(FuelEngraved.KEY_EXTRA_MODIFIERS,
                ModifierNBT.builder().add(modifiersToAdd).build().serializeToNBT());
        result.addModifier(TiAcUModifiers.FUEL_ENGRAVED.getId(),1);
        return true;
    }
    default boolean processToolResult(ToolStack result, ToolStack lens,IFuelLensItem fuelLens, Player player, FluidStack fuel, FuelEngraverBlockEntity blockEntity){
        return this.processToolResult(result,lens,fuelLens,player,fuel,blockEntity.getTank());
    }

    default int getBasicFuelConsumption(ToolStack lens, IFuelLensItem fuelLens,@Nullable FluidStack fluidStack){
        if (fluidStack!=null) {
            var fluid = fluidStack.getFluid();
            if (fluid.isSame(TinkerFluids.venom.get())) return CFG_AMOUNT_REMOVE_ENGRAVE;
        }
        AtomicInteger atomicInteger = new AtomicInteger(0);
        lens.getModifierList().stream()
                .filter(entry -> !ModifierManager.isInTag(entry.getId(), TiAcUTagkeys.Modifiers.ENGRAVER_BLACKLIST))
                .map(entry -> entry.getLevel()*CFG_AMOUNT_EACH_LEVEL).forEach(atomicInteger::addAndGet);
        return atomicInteger.get();
    }
    default int getTempRequirement(ToolStack lens, IFuelLensItem fuelLens,@Nullable FluidStack fluidStack){
        if (fluidStack!=null) {
            var fluid = fluidStack.getFluid();
            if (fluid.isSame(TinkerFluids.venom.get())) return 0;
        }
        return (int) lens.getModifierList().stream()
                .filter(entry -> !ModifierManager.isInTag(entry.getId(), TiAcUTagkeys.Modifiers.ENGRAVER_BLACKLIST))
                .count()*CFG_TEMP_EACH_MODIFIER;
    }
}
