package com.c2h6s.tinkers_advanced_utilities.data.providers;

import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import com.c2h6s.tinkers_advanced_utilities.init.TiAcUItems;
import net.minecraft.data.PackOutput;
import slimeknights.tconstruct.library.data.tinkering.AbstractToolDefinitionDataProvider;
import slimeknights.tconstruct.library.tools.definition.module.build.ToolSlotsModule;

public class TiAcUToolDefinitionProvider extends AbstractToolDefinitionDataProvider {
    public TiAcUToolDefinitionProvider(PackOutput packOutput) {
        super(packOutput, TinkersAdvanced.MODID);
    }

    @Override
    protected void addToolDefinitions() {
        TiAcUItems.SIMPLE_LENS_MAP.forEach((obj,map)->{
            var slotModule = ToolSlotsModule.builder();
            map.forEach(slotModule::slots);
            define(obj.getId()).module(slotModule.build()).build();
        });
    }

    @Override
    public String getName() {
        return "TiAcU Tool Definition Provider";
    }
}
