package com.c2h6s.tinkers_advanced_utilities.data.providers;

import com.c2h6s.etstlib.register.EtSTLibToolStat;
import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import com.c2h6s.tinkers_advanced_utilities.init.TiAcUItems;
import net.minecraft.data.PackOutput;
import slimeknights.tconstruct.library.data.tinkering.AbstractToolDefinitionDataProvider;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierId;
import slimeknights.tconstruct.library.tools.definition.module.build.MultiplyStatsModule;
import slimeknights.tconstruct.library.tools.definition.module.build.SetStatsModule;
import slimeknights.tconstruct.library.tools.definition.module.build.ToolSlotsModule;
import slimeknights.tconstruct.library.tools.definition.module.build.ToolTraitsModule;
import slimeknights.tconstruct.library.tools.nbt.MultiplierNBT;
import slimeknights.tconstruct.library.tools.nbt.StatsNBT;
import slimeknights.tconstruct.tools.data.ModifierIds;

import java.util.List;

public class TiAcUToolDefinitionProvider extends AbstractToolDefinitionDataProvider {
    public TiAcUToolDefinitionProvider(PackOutput packOutput) {
        super(packOutput, TinkersAdvanced.MODID);
    }

    @Override
    protected void addToolDefinitions() {
        TiAcUItems.SIMPLE_LENS_MAP.forEach((obj,map)->{
            var slotModule = ToolSlotsModule.builder();
            map.forEach(slotModule::slots);
            var builder = define(obj.getId()).module(slotModule.build());
            if (obj.get()==TiAcUItems.SIMPLE_EMERALD_LENS.get()) builder.module(new SetStatsModule(StatsNBT.builder()
                    .set(EtSTLibToolStat.FLUID_EFFICIENCY,-0.25f).build()));
            if (obj.get()==TiAcUItems.SIMPLE_QUARTZ_LENS.get()){
                builder.module(new ToolTraitsModule(List.of(new ModifierEntry(ModifierIds.keen,1))));
                builder.module(new SetStatsModule(StatsNBT.builder()
                        .set(EtSTLibToolStat.FLUID_EFFICIENCY,0.25f).build()));
            }
            builder.build();
        });
    }

    @Override
    public String getName() {
        return "TiAcU Tool Definition Provider";
    }
}
