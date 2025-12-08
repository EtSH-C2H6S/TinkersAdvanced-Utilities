package com.c2h6s.tinkers_advanced_utilities.data.providers;

import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import com.c2h6s.tinkers_advanced_utilities.TinkersAdvancedUtilities;
import com.c2h6s.tinkers_advanced_utilities.init.TiAcUItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.Tags;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.fluids.TinkerFluids;
import slimeknights.tconstruct.library.data.recipe.ISmelteryRecipeHelper;
import slimeknights.tconstruct.library.recipe.casting.ItemCastingRecipeBuilder;
import slimeknights.tconstruct.shared.TinkerCommons;
import slimeknights.tconstruct.shared.TinkerMaterials;
import slimeknights.tconstruct.smeltery.TinkerSmeltery;
import slimeknights.tconstruct.world.TinkerWorld;

import java.util.function.Consumer;

public class TiAcURecipeProvider extends RecipeProvider implements ISmelteryRecipeHelper {
    public TiAcURecipeProvider(PackOutput generator) {
        super(generator);
    }
    public static final ResourceLocation baseFolder = new ResourceLocation(TinkersAdvanced.MODID,"utilities/");
    public static ResourceLocation namedFolder(String name){
        return ResourceLocation.tryParse(baseFolder+name+"/"+name);
    }
    public static ResourceLocation modifierFolder(String name){
        return ResourceLocation.tryParse(TinkersAdvanced.getLocation("modifiers/")+name);
    }
    public static ResourceLocation salvageFolder(String name){
        return ResourceLocation.tryParse(TinkersAdvanced.getLocation("modifiers/salvage/")+name);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        ResourceLocation folder;
        Consumer<FinishedRecipe> conditional;
        folder = baseFolder;
        ItemCastingRecipeBuilder.basinRecipe(TiAcUItems.ROSE_GOLD_BASIN.get())
                .setCast(TinkerSmeltery.scorchedBasin.asItem(),true)
                .setFluid(TinkerFluids.moltenRoseGold.getTag(), 270)
                .setCoolingTime(270).save(consumer,new ResourceLocation(folder+"/rose_gold_basin"));
        ItemCastingRecipeBuilder.basinRecipe(TiAcUItems.ROSE_GOLD_TABLE.get())
                .setCast(TinkerSmeltery.scorchedTable.asItem(),true)
                .setFluid(TinkerFluids.moltenRoseGold.getTag(), 270)
                .setCoolingTime(270).save(consumer,new ResourceLocation(folder+"/rose_gold_table"));
        ItemCastingRecipeBuilder.basinRecipe(TiAcUItems.ROSE_GOLD_FAUCET.get())
                .setCast(TinkerSmeltery.scorchedFaucet.asItem(),true)
                .setFluid(TinkerFluids.moltenRoseGold.getTag(), 90)
                .setCoolingTime(180).save(consumer,new ResourceLocation(folder+"/rose_gold_fauset"));

        ItemCastingRecipeBuilder.basinRecipe(TiAcUItems.HEPATIZON_BASIN.get())
                .setCast(TiAcUItems.ROSE_GOLD_BASIN.get(),true)
                .setFluid(TinkerFluids.moltenHepatizon.getTag(), 270)
                .setCoolingTime(270).save(consumer,new ResourceLocation(folder+"/hepatizon_basin"));
        ItemCastingRecipeBuilder.basinRecipe(TiAcUItems.HEPATIZON_TABLE.get())
                .setCast(TiAcUItems.ROSE_GOLD_TABLE.get(),true)
                .setFluid(TinkerFluids.moltenHepatizon.getTag(), 270)
                .setCoolingTime(270).save(consumer,new ResourceLocation(folder+"/hepatizon_table"));
        ItemCastingRecipeBuilder.basinRecipe(TiAcUItems.HEPATIZON_FAUCET.get())
                .setCast(TiAcUItems.ROSE_GOLD_FAUCET.get(),true)
                .setFluid(TinkerFluids.moltenHepatizon.getTag(), 90)
                .setCoolingTime(180).save(consumer,new ResourceLocation(folder+"/hepatizon_faucet"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC,TiAcUItems.EXCHANGER.get()).unlockedBy("has_item", has(TinkerSmeltery.scorchedProxyTank))
                .pattern("AAA").pattern("BCB").pattern("AAA")
                .define('A', TinkerSmeltery.scorchedBrick).define('B', TinkerWorld.enderGeode.get())
                .define('C',TinkerSmeltery.scorchedProxyTank).save(consumer,new ResourceLocation(folder+"/exchanger"));
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC,TiAcUItems.ADVANCED_ALLOYER.get()).unlockedBy("has_item", has(TinkerSmeltery.scorchedAlloyer))
                .pattern("ABA").pattern("BCB").pattern("ABA")
                .define('A', TinkerMaterials.hepatizon.getIngot()).define('B', TinkerSmeltery.scorchedGlassPane)
                .define('C',TinkerSmeltery.scorchedAlloyer).save(consumer,new ResourceLocation(folder+"/advanced_alloyer"));
    }

    @Override
    public String getModId() {
        return TinkersAdvancedUtilities.MODID;
    }
}
