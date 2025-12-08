package com.c2h6s.tinkers_advanced_utilities.content.event.handler;

import com.c2h6s.tinkers_advanced_utilities.init.TiAcUItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class TiAcUPlayerEventHandler {
    @SubscribeEvent
    public static void addItemTooltips(ItemTooltipEvent event){
        var stack = event.getItemStack();
        if (stack.is(TiAcUItems.EXCHANGER.get())){
            event.getToolTip().add(Component.translatable("tooltip.tinkers_advanced.exchanger1").withStyle(ChatFormatting.GRAY));
            event.getToolTip().add(Component.translatable("tooltip.tinkers_advanced.exchanger3").withStyle(ChatFormatting.GRAY));
        }
        if (stack.is(TiAcUItems.ADVANCED_ALLOYER.get())){
            event.getToolTip().add(Component.translatable("tooltip.tinkers_advanced.advanced_alloyer1")
                    .withStyle(ChatFormatting.GRAY));
            event.getToolTip().add(Component.translatable("tooltip.tinkers_advanced.advanced_alloyer2")
                    .withStyle(ChatFormatting.GRAY));
        }
    }
}
