package com.c2h6s.tinkers_advanced_utilities;

import com.c2h6s.tinkers_advanced_utilities.init.TiAcUItems;
import net.minecraftforge.eventbus.api.IEventBus;

public class TiAcUModule {
    public static void register(IEventBus bus){
        TiAcUItems.SIMPLE_LENS.register(bus);
    }
}
