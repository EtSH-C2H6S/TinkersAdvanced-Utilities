package com.c2h6s.tinkers_advanced_utilities.content.item;

import com.c2h6s.etstlib.register.EtSTLibToolStat;
import com.c2h6s.tinkers_advanced_utilities.api.interfaces.IFuelLensItem;
import com.c2h6s.tinkers_advanced_utilities.content.block.blockEntity.FuelEngraverBlockEntity;
import com.c2h6s.tinkers_advanced_utilities.data.TiAcUTagkeys;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import slimeknights.mantle.client.TooltipKey;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.ModifierManager;
import slimeknights.tconstruct.library.tools.definition.ToolDefinition;
import slimeknights.tconstruct.library.tools.helper.TooltipBuilder;
import slimeknights.tconstruct.library.tools.item.ModifiableItem;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;
import slimeknights.tconstruct.plugin.jei.melting.MeltingFuelHandler;

import java.util.List;
import java.util.function.Predicate;

public class FuelLensItem extends ModifiableItem implements IFuelLensItem {
    public final Predicate<ItemStack> suitableTool;
    public FuelLensItem(ToolDefinition toolDefinition, Predicate<ItemStack> suitableTool) {
        super(new Properties().stacksTo(1), toolDefinition);
        this.suitableTool = suitableTool;
    }

    @Override
    public List<Component> getStatInformation(IToolStackView tool, @Nullable Player player, List<Component> tooltips, TooltipKey key, TooltipFlag tooltipFlag) {
        tooltips = new TooltipBuilder(tool,tooltips).add(EtSTLibToolStat.FLUID_EFFICIENCY).addAllFreeSlots().getTooltips();
        for (ModifierEntry entry : tool.getModifierList()) {
            entry.getHook(ModifierHooks.TOOLTIP).addTooltip(tool, entry, player, tooltips, key, tooltipFlag);
        }
        if (!(tool.getItem() instanceof IFuelLensItem fuelLens)) return tooltips;
        float consumption = fuelLens.getBasicFuelConsumption((ToolStack) tool,fuelLens,null);
        var temp = fuelLens.getTempRequirement((ToolStack) tool,fuelLens,null);
        consumption*=1-tool.getStats().get(EtSTLibToolStat.FLUID_EFFICIENCY);
        int roundConsumption = (int) consumption<consumption?(int) consumption+1:(int) consumption;
        tooltips.add(Component.translatable("tooltip.tinkers_advanced.lens_consumption").append(""+roundConsumption)
                .withStyle(style -> style.withColor(0xDFFF7C)));
        tooltips.add(Component.translatable("tooltip.tinkers_advanced.lens_temp").append(""+temp)
                .withStyle(style -> style.withColor(0xFFE179)));
        boolean hasWarn1 = consumption>FuelEngraverBlockEntity.CFG_CAPACITY;
        boolean hasWarn2 = MeltingFuelHandler.getUsableFuels(temp).isEmpty();
        if (hasWarn2||hasWarn1) tooltips.add(Component.translatable("tooltip.tinkers_advanced.lens_warn")
                .withStyle(style -> style.withColor(0xFF0000)));
        if (hasWarn1) tooltips.add(Component.translatable("tooltip.tinkers_advanced.lens_consumption_warn")
                .withStyle(ChatFormatting.RED));
        if (hasWarn2) tooltips.add(Component.translatable("tooltip.tinkers_advanced.lens_temp_warn")
                .withStyle(ChatFormatting.RED));
        if (hasWarn2||hasWarn1) tooltips.add(Component.translatable("tooltip.tinkers_advanced.lens_warn2")
                .withStyle(style -> style.withColor(0xFF0000)));
        return tooltips;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        if (stack.is(TinkerTags.Items.MELEE)||stack.is(TinkerTags.Items.MELEE_PRIMARY)){
            tooltip.add(Component.translatable("tooltip.tinkers_advanced.lens_melee").withStyle(ChatFormatting.GRAY));
        }
        if (stack.is(TinkerTags.Items.RANGED)){
            tooltip.add(Component.translatable("tooltip.tinkers_advanced.lens_ranged").withStyle(ChatFormatting.GRAY));
        }
        if (stack.is(TinkerTags.Items.AMMO)){
            tooltip.add(Component.translatable("tooltip.tinkers_advanced.lens_ammo").withStyle(ChatFormatting.GRAY));
        }
        if (stack.is(TinkerTags.Items.ARMOR)){
            tooltip.add(Component.translatable("tooltip.tinkers_advanced.lens_armor").withStyle(ChatFormatting.GRAY));
        }
    }

    @Override
    public Predicate<ItemStack> getItemPredicate() {
        return this.suitableTool;
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, Player player, Entity target) {
        return false;
    }
}
