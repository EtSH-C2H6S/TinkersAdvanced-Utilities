package com.c2h6s.tinkers_advanced_utilities.client.event.handler;

import com.c2h6s.tinkers_advanced_utilities.content.block.blockEntity.FuelEngraverBlockEntity;
import com.c2h6s.tinkers_advanced_utilities.init.TiAcUBlockEntities;
import com.c2h6s.tinkers_advanced_utilities.client.renderer.blockEntity.ExchangerBERenderer;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import slimeknights.tconstruct.smeltery.client.render.CastingBlockEntityRenderer;
import slimeknights.tconstruct.smeltery.client.render.FaucetBlockEntityRenderer;
import slimeknights.tconstruct.smeltery.client.render.TankBlockEntityRenderer;
import slimeknights.tconstruct.smeltery.client.render.TankInventoryBlockEntityRenderer;


@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class TiAcUClientModEventHandler {
    @SubscribeEvent
    public static void registerEntityRenderer(EntityRenderersEvent.RegisterRenderers event){
        event.registerBlockEntityRenderer(TiAcUBlockEntities.EXCHANGER_BLOCK_ENTITY.get(), pContext -> new ExchangerBERenderer());
        event.registerBlockEntityRenderer(TiAcUBlockEntities.ROSE_GOLD_SLIME_BASIN.get(), CastingBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(TiAcUBlockEntities.ROSE_GOLD_TABLE.get(), CastingBlockEntityRenderer::new);

        event.registerBlockEntityRenderer(TiAcUBlockEntities.HEPATIZON_BASIN.get(), CastingBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(TiAcUBlockEntities.HEPATIZON_TABLE.get(), CastingBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(TiAcUBlockEntities.ROSE_GOLD_FAUCET.get(), FaucetBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(TiAcUBlockEntities.HEPATIZON_FAUCET.get(), FaucetBlockEntityRenderer::new);

        event.registerBlockEntityRenderer(TiAcUBlockEntities.ADVANCED_ALLOYER.get(), TankBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(TiAcUBlockEntities.FUEL_ENGRAVER.get(),ctx-> new TankInventoryBlockEntityRenderer<>(BlockStateProperties.HORIZONTAL_FACING));
    }
}
