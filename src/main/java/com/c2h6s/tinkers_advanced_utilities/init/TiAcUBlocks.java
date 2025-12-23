package com.c2h6s.tinkers_advanced_utilities.init;

import com.c2h6s.tinkers_advanced.core.TiAcCrModule;
import com.c2h6s.tinkers_advanced.core.content.event.TiAcLoadRegistryClassEvent;
import com.c2h6s.tinkers_advanced_utilities.TiAcUConfig;
import com.c2h6s.tinkers_advanced_utilities.content.block.*;
import com.c2h6s.tinkers_advanced_utilities.content.block.blockEntity.*;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import slimeknights.mantle.util.BlockEntityHelper;
import slimeknights.tconstruct.smeltery.TinkerSmeltery;
import slimeknights.tconstruct.smeltery.block.CastingBasinBlock;
import slimeknights.tconstruct.smeltery.block.CastingTableBlock;
import slimeknights.tconstruct.smeltery.block.CastingTankBlock;
import slimeknights.tconstruct.smeltery.block.controller.AlloyerBlock;
import slimeknights.tconstruct.smeltery.block.entity.component.TankBlockEntity;

import java.util.List;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class TiAcUBlocks {
    @SubscribeEvent
    public static void init(TiAcLoadRegistryClassEvent event){}
    public static final RegistryObject<Block> EXCHANGER = TiAcCrModule.BLOCKS.register("exchanger", () -> new ExchangerBlock(BlockBehaviour.Properties.copy(TinkerSmeltery.searedBricks.get()).explosionResistance(10000).sound(SoundType.GLASS).noOcclusion()));
    public static final RegistryObject<Block> ROSE_GOLD_FAUCET = TiAcCrModule.BLOCKS.register("rose_gold_faucet", () ->
            new RoseGoldFaucetBlock(BlockBehaviour.Properties.copy(TinkerSmeltery.searedFaucet.get()).sound(SoundType.METAL)));
    public static final RegistryObject<Block> HEPATIZON_FAUCET = TiAcCrModule.BLOCKS.register("hepatizon_faucet", () ->
            new HepatizonFaucetBlock(BlockBehaviour.Properties.copy(TinkerSmeltery.searedFaucet.get()).sound(SoundType.NETHERITE_BLOCK)));
    public static final RegistryObject<Block> ROSE_GOLD_TABLE = TiAcCrModule.BLOCKS.register("rose_gold_casting_table", () ->
            new CastingTableBlock(BlockBehaviour.Properties.copy(TinkerSmeltery.searedTable.get())
                    .sound(SoundType.METAL),true) {
                @Override
                public List<ItemStack> getDrops(BlockState pState, LootParams.Builder pParams) {
                    return List.of(new ItemStack(this.asItem()));
                }

                @Override
                public @NotNull BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
                    return new RoseGoldCastingBlockEntity.Table(pPos,pState);
                }

                @Override
                public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> check) {
                    return RoseGoldCastingBlockEntity.getTicker(pLevel,check, TiAcUBlockEntities.ROSE_GOLD_TABLE.get());
                }
                @Override
                public void appendHoverText(ItemStack pStack, @Nullable BlockGetter pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
                    int temp = TiAcUConfig.COMMON.ROSE_GOLD_TABLE_SEPARATION.get();
                    float low = TiAcUConfig.COMMON.ROSE_GOLD_CASTING_INCREASE.get().floatValue();
                    float high = TiAcUConfig.COMMON.ROSE_GOLD_CASTING_DECREASE.get().floatValue();
                    pTooltip.add(Component.translatable("tooltip.tinkers_advanced.rose_gold_casting",temp/20,String.format("%.1f",low),String.format("%.1f",high)).withStyle(ChatFormatting.GRAY));
                }
            });
    public static final RegistryObject<Block> ROSE_GOLD_BASIN = TiAcCrModule.BLOCKS.register("rose_gold_casting_basin", () ->
            new CastingBasinBlock(BlockBehaviour.Properties.copy(TinkerSmeltery.searedTable.get())
                    .sound(SoundType.METAL),true) {
                @Override
                public List<ItemStack> getDrops(BlockState pState, LootParams.Builder pParams) {
                    return List.of(new ItemStack(this.asItem()));
                }

                @Override
                public @NotNull BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
                    return new RoseGoldCastingBlockEntity.Basin(pPos,pState);
                }

                @Override
                public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> check) {
                    return RoseGoldCastingBlockEntity.getTicker(pLevel,check, TiAcUBlockEntities.ROSE_GOLD_SLIME_BASIN.get());
                }

                @Override
                public void appendHoverText(ItemStack pStack, @Nullable BlockGetter pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
                    int temp = TiAcUConfig.COMMON.ROSE_GOLD_BASIN_SEPARATION.get();
                    float low = TiAcUConfig.COMMON.ROSE_GOLD_CASTING_INCREASE.get().floatValue();
                    float high = TiAcUConfig.COMMON.ROSE_GOLD_CASTING_DECREASE.get().floatValue();
                    pTooltip.add(Component.translatable("tooltip.tinkers_advanced.rose_gold_casting",temp/20,String.format("%.1f",low),String.format("%.1f",high)).withStyle(ChatFormatting.GRAY));
                }
            });
    public static final RegistryObject<Block> HEPATIZON_TABLE = TiAcCrModule.BLOCKS.register("hepatizon_casting_table", () ->
            new CastingTableBlock(BlockBehaviour.Properties.copy(TinkerSmeltery.searedTable.get())
                    .sound(SoundType.NETHERITE_BLOCK),true) {
                @Override
                public List<ItemStack> getDrops(BlockState pState, LootParams.Builder pParams) {
                    return List.of(new ItemStack(this.asItem()));
                }

                @Override
                public @NotNull BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
                    return new HepatizonCastingBlockEntity.Table(pPos,pState);
                }

                @Override
                public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> check) {
                    return HepatizonCastingBlockEntity.getTicker(pLevel,check, TiAcUBlockEntities.HEPATIZON_TABLE.get());
                }
                @Override
                public void appendHoverText(ItemStack pStack, @Nullable BlockGetter pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
                    float high = TiAcUConfig.COMMON.HEPATIZON_CASTING_SPEED.get().floatValue();
                    pTooltip.add(Component.translatable("tooltip.tinkers_advanced.hepatizon_casting",String.format("%.1f",high)).withStyle(ChatFormatting.GRAY));
                }
            });
    public static final RegistryObject<Block> HEPATIZON_BASIN = TiAcCrModule.BLOCKS.register("hepatizon_casting_basin", () ->
            new CastingBasinBlock(BlockBehaviour.Properties.copy(TinkerSmeltery.searedTable.get())
                    .sound(SoundType.NETHERITE_BLOCK),true) {
                @Override
                public List<ItemStack> getDrops(BlockState pState, LootParams.Builder pParams) {
                    return List.of(new ItemStack(this.asItem()));
                }

                @Override
                public @NotNull BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
                    return new HepatizonCastingBlockEntity.Basin(pPos,pState);
                }

                @Override
                public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> check) {
                    return HepatizonCastingBlockEntity.getTicker(pLevel,check, TiAcUBlockEntities.HEPATIZON_BASIN.get());
                }
                @Override
                public void appendHoverText(ItemStack pStack, @Nullable BlockGetter pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
                    float high = TiAcUConfig.COMMON.HEPATIZON_CASTING_SPEED.get().floatValue();
                    pTooltip.add(Component.translatable("tooltip.tinkers_advanced.hepatizon_casting",String.format("%.1f",high)).withStyle(ChatFormatting.GRAY));
                }
            });

    public static final RegistryObject<Block> ADVANCED_ALLOYER = TiAcCrModule.BLOCKS.register("advanced_alloyer", () ->
            new AlloyerBlock(BlockBehaviour.Properties.copy(TinkerSmeltery.scorchedFaucet.get()).sound(SoundType.NETHERITE_BLOCK)){
                @Override
                public @NotNull BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
                    return new AdvancedAlloyerBlockEntity(pPos,pState);
                }
                @Override
                public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> wanted) {
                    return AdvancedAlloyerBlockEntity.getTicker(pLevel,wanted,TiAcUBlockEntities.ADVANCED_ALLOYER.get());
                }

                @Override
                public List<ItemStack> getDrops(BlockState p_287732_, LootParams.Builder p_287596_) {
                    return List.of(new ItemStack(this.asItem()));
                }
            });

    public static final RegistryObject<FuelEngraverBlock> FUEL_ENGRAVER = TiAcCrModule.BLOCKS.register("fuel_engraver", () ->
            new FuelEngraverBlock(BlockBehaviour.Properties.copy(TinkerSmeltery.scorchedFaucet.get()).sound(SoundType.NETHERITE_BLOCK)));
}
