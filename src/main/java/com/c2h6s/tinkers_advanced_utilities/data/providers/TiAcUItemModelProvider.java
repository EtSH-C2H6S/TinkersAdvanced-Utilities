package com.c2h6s.tinkers_advanced_utilities.data.providers;

import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import com.c2h6s.tinkers_advanced_utilities.TinkersAdvancedUtilities;
import com.c2h6s.tinkers_advanced_utilities.content.item.FuelLensItem;
import com.c2h6s.tinkers_advanced_utilities.init.TiAcUItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.loaders.DynamicFluidContainerModelBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.RegistryObject;
import slimeknights.mantle.registration.object.FluidObject;
import slimeknights.mantle.registration.object.ItemObject;

import static com.c2h6s.tinkers_advanced.core.init.TiAcCrItem.*;

public class TiAcUItemModelProvider extends ItemModelProvider {
    public static final String PARENT_SIMPLE_ITEM ="item/generated";
    public static final String PARENT_BUCKET_FLUID ="forge:item/bucket_drip";

    public TiAcUItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, TinkersAdvanced.MODID, existingFileHelper);
    }

    public void generateItemModel(RegistryObject<? extends Item> object,String typePath){
        withExistingParent( object.getId().getPath(), PARENT_SIMPLE_ITEM).texture("layer0",getItemLocation(object.getId().getPath(),typePath));
    }
    public void generateItemModel(ItemObject<?> object, String typePath){
        withExistingParent( object.getId().getPath(), PARENT_SIMPLE_ITEM).texture("layer0",getItemLocation(object.getId().getPath(),typePath));
    }
    public void generateBlockItemModel(RegistryObject<BlockItem> object){
        withExistingParent(object.getId().getPath(), getBlockItemLocation(object.getId().getPath()));
    }
    public void generateBucketItemModel(FluidObject<ForgeFlowingFluid> object,boolean flip){
        withExistingParent(object.getId().getPath()+"_bucket",PARENT_BUCKET_FLUID).customLoader((itemModelBuilder,existingFileHelper)->DynamicFluidContainerModelBuilder
                .begin(itemModelBuilder,existingFileHelper)
                .fluid(object.get())
                .flipGas(flip));
    }

    public ResourceLocation getItemLocation(String path,String typePath){
        return new ResourceLocation(TinkersAdvanced.MODID,"item/"+typePath+"/"+path);
    }
    public ResourceLocation getBlockItemLocation(String path){
        return new ResourceLocation(TinkersAdvanced.MODID,"block/"+path);
    }

    @Override
    protected void registerModels() {
        for (RegistryObject<FuelLensItem> object: TiAcUItems.SIMPLE_LENS_MAP.keySet()){
            generateItemModel(object,"lens");
        }
//        for (RegistryObject<BlockItem> object:getListSimpleBlock(TinkersAdvancedUtilities.MODID)){
//            generateBlockItemModel(object);
//        }
        withExistingParent("rose_gold_casting_basin",TinkersAdvanced.getLocation("block/smeltery/rose_gold/basin"));
        withExistingParent("rose_gold_casting_table",TinkersAdvanced.getLocation("block/smeltery/rose_gold/table"));
        withExistingParent("rose_gold_faucet",TinkersAdvanced.getLocation("block/smeltery/rose_gold/faucet"));
        withExistingParent("hepatizon_casting_basin",TinkersAdvanced.getLocation("block/smeltery/hepatizon/basin"));
        withExistingParent("hepatizon_casting_table",TinkersAdvanced.getLocation("block/smeltery/hepatizon/table"));
        withExistingParent("hepatizon_faucet",TinkersAdvanced.getLocation("block/smeltery/hepatizon/faucet"));
        withExistingParent("advanced_alloyer",TinkersAdvanced.getLocation("block/smeltery/advanced_alloyer/alloyer_inactive"));
        withExistingParent("fuel_engraver",TinkersAdvanced.getLocation("block/fuel_engraver"));
    }
}
