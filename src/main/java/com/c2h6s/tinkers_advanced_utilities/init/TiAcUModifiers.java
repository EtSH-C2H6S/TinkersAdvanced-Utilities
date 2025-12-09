package com.c2h6s.tinkers_advanced_utilities.init;

import com.c2h6s.tinkers_advanced.core.content.event.TiAcLoadRegistryClassEvent;
import com.c2h6s.tinkers_advanced_utilities.TinkersAdvancedUtilities;
import com.c2h6s.tinkers_advanced_utilities.content.tool.modifiers.FuelEngraved;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.util.StaticModifier;

import static com.c2h6s.tinkers_advanced.core.TiAcCrModule.MODIFIERS;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD,modid = TinkersAdvancedUtilities.MODID)
public class TiAcUModifiers {
    @SubscribeEvent
    public static void inti(TiAcLoadRegistryClassEvent event){}

    public static final StaticModifier<FuelEngraved> FUEL_ENGRAVED = MODIFIERS.register("fuel_engraved",FuelEngraved::new);
    public static final StaticModifier<Modifier> WATER_WASHED = MODIFIERS.register("water_washed",Modifier::new);
}
