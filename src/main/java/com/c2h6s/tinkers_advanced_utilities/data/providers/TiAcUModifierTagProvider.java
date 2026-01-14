package com.c2h6s.tinkers_advanced_utilities.data.providers;

import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import com.c2h6s.tinkers_advanced_utilities.data.TiAcUModifierIDs;
import com.c2h6s.tinkers_advanced_utilities.data.TiAcUTagkeys;
import com.c2h6s.tinkers_advanced_utilities.init.TiAcUModifiers;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.data.tinkering.AbstractModifierTagProvider;

public class TiAcUModifierTagProvider extends AbstractModifierTagProvider {
    public TiAcUModifierTagProvider(PackOutput packOutput, ExistingFileHelper existingFileHelper) {
        super(packOutput, TinkersAdvanced.MODID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        tag(TiAcUTagkeys.Modifiers.ENGRAVER_BLACKLIST)
                .add(TiAcUModifiers.FUEL_ENGRAVED.getId())
                .addOptional(TiAcUModifierIDs.LENS_EFFICIENCY);
        tag(TinkerTags.Modifiers.SLOTLESS)
                .addOptional(
                        TiAcUModifiers.WATER_WASHED.getId(),
                        TiAcUModifierIDs.LENS_EFFICIENCY
                );
        tag(TinkerTags.Modifiers.GENERAL_SLOTLESS)
                .addOptional(
                        TiAcUModifiers.WATER_WASHED.getId(),
                        TiAcUModifierIDs.LENS_EFFICIENCY
                );
    }

    @Override
    public String getName() {
        return "TiAcU Modifier Tag Provider";
    }
}
