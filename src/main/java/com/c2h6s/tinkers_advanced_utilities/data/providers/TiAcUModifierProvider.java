package com.c2h6s.tinkers_advanced_utilities.data.providers;

import com.c2h6s.etstlib.register.EtSTLibToolStat;
import net.minecraft.data.PackOutput;
import slimeknights.tconstruct.library.data.tinkering.AbstractModifierProvider;
import slimeknights.tconstruct.library.modifiers.modules.build.StatBoostModule;

import static com.c2h6s.tinkers_advanced_utilities.data.TiAcUModifierIDs.*;

public class TiAcUModifierProvider extends AbstractModifierProvider {
    public TiAcUModifierProvider(PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    protected void addModifiers() {
        buildModifier(LENS_EFFICIENCY).addModule(StatBoostModule.add(EtSTLibToolStat.FLUID_EFFICIENCY).amount(0,0.1f)).build();
    }

    @Override
    public String getName() {
        return "TiAcU Modifier Provider";
    }
}
