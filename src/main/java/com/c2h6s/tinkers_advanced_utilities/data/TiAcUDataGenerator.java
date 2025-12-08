package com.c2h6s.tinkers_advanced_utilities.data;

import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import com.c2h6s.tinkers_advanced_utilities.TinkersAdvancedUtilities;
import com.c2h6s.tinkers_advanced_utilities.data.providers.*;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TinkersAdvancedUtilities.MODID,bus=Mod.EventBusSubscriber.Bus.MOD)
public class TiAcUDataGenerator {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event){
        DataGenerator generator=event.getGenerator();
        PackOutput output=generator.getPackOutput();
        ExistingFileHelper helper=event.getExistingFileHelper();
        var lookupProvider = event.getLookupProvider();

        generator.addProvider(event.includeClient(),new TiAcUBlockStateProvider(output,TinkersAdvanced.MODID,helper));
        generator.addProvider(event.includeClient(),new TiAcUItemModelProvider(output,helper));
        TiAcUBlockTagProvider blockTags = new TiAcUBlockTagProvider(output, lookupProvider, helper);
        generator.addProvider(event.includeClient(),blockTags);
        generator.addProvider(event.includeClient(),new TiAcUItemTagProvider(output,lookupProvider,blockTags.contentsGetter()));
        generator.addProvider(event.includeClient(),new TiAcURecipeProvider(output));
        generator.addProvider(event.includeClient(),new TiAcUModifierTagProvider(output,helper));
        generator.addProvider(event.includeClient(), new TiAcUToolDefinitionProvider(output));
    }
}
