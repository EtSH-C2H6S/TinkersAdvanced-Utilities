package com.c2h6s.tinkers_advanced_utilities.init;

import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import com.c2h6s.tinkers_advanced.core.content.event.TiAcLoadRegistryClassEvent;
import com.c2h6s.tinkers_advanced_utilities.TinkersAdvancedUtilities;
import com.c2h6s.tinkers_advanced_utilities.content.item.FuelLensItem;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import slimeknights.tconstruct.library.tools.SlotType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.c2h6s.tinkers_advanced.core.init.TiAcCrItem.*;
import static slimeknights.tconstruct.common.TinkerTags.Items.*;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class TiAcUItems {
    public static final DeferredRegister<Item> SIMPLE_LENS = DeferredRegister.create(ForgeRegistries.ITEMS, TinkersAdvanced.MODID);
    public static final Map<RegistryObject<FuelLensItem>,Map<SlotType,Integer>> SIMPLE_LENS_MAP = new HashMap<>();
    public static final Map<RegistryObject<FuelLensItem>, List<TagKey<Item>>> SIMPLE_LENS_TAGS = new HashMap<>();
    public static RegistryObject<FuelLensItem> registerSimpleLens(String name,Map<SlotType,Integer> map,List<TagKey<Item>> tagWL){
        var obj = SIMPLE_LENS.register(name,()->new FuelLensItem(TiAcUToolDefinitions.name(name),
                stack -> tagWL.stream().anyMatch(stack::is)));
        SIMPLE_LENS_TAGS.put(obj,tagWL);
        SIMPLE_LENS_MAP.put(obj,map);
        return obj;
    }

    public static final RegistryObject<BlockItem> EXCHANGER = registerBlockItem(TinkersAdvancedUtilities.MODID,ITEMS, TiAcUBlocks.EXCHANGER);
    public static final RegistryObject<BlockItem> ROSE_GOLD_FAUCET = registerBlockItem(TinkersAdvancedUtilities.MODID,ITEMS, TiAcUBlocks.ROSE_GOLD_FAUCET);
    public static final RegistryObject<BlockItem> HEPATIZON_FAUCET = registerBlockItem(TinkersAdvancedUtilities.MODID,ITEMS, TiAcUBlocks.HEPATIZON_FAUCET);
    public static final RegistryObject<BlockItem> ROSE_GOLD_TABLE = registerBlockItem(TinkersAdvancedUtilities.MODID,ITEMS, TiAcUBlocks.ROSE_GOLD_TABLE);
    public static final RegistryObject<BlockItem> ROSE_GOLD_BASIN = registerBlockItem(TinkersAdvancedUtilities.MODID,ITEMS, TiAcUBlocks.ROSE_GOLD_BASIN);
    public static final RegistryObject<BlockItem> HEPATIZON_TABLE = registerBlockItem(TinkersAdvancedUtilities.MODID,ITEMS, TiAcUBlocks.HEPATIZON_TABLE);
    public static final RegistryObject<BlockItem> HEPATIZON_BASIN = registerBlockItem(TinkersAdvancedUtilities.MODID,ITEMS, TiAcUBlocks.HEPATIZON_BASIN);
    public static final RegistryObject<BlockItem> ADVANCED_ALLOYER = registerBlockItem(TinkersAdvancedUtilities.MODID,ITEMS, TiAcUBlocks.ADVANCED_ALLOYER);
    public static final RegistryObject<BlockItem> FUEL_ENGRAVER = registerBlockItem(TinkersAdvancedUtilities.MODID,ITEMS, TiAcUBlocks.FUEL_ENGRAVER);

    public static final RegistryObject<FuelLensItem> SIMPLE_QUARTZ_LENS = registerSimpleLens("simple_quartz_lens",
            buildSlots().addSlot(SlotType.UPGRADE,1).addSlot(SlotType.ABILITY,1).build(),
            List.of(MELEE,MELEE_PRIMARY));

    @SubscribeEvent
    public static void init(TiAcLoadRegistryClassEvent event){}

    public static SlotMapBuilder buildSlots(){
        return new SlotMapBuilder();
    }

    public static class SlotMapBuilder{
        private final Map<SlotType,Integer> slotMap = new HashMap<>();
        public SlotMapBuilder addSlot(SlotType slot,int count){
            this.slotMap.put(slot,count);
            return this;
        }
        public Map<SlotType,Integer> build(){
            return new HashMap<>(slotMap);
        }
    }
}
