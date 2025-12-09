package com.c2h6s.tinkers_advanced_utilities.data;

import com.c2h6s.tinkers_advanced.TinkersAdvanced;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierManager;

public class TiAcUTagkeys {
    public static class Modifiers{
        private static TagKey<Modifier> name(String name) {
            return ModifierManager.getTag(TinkersAdvanced.getLocation(name));
        }

        public static final TagKey<Modifier> ENGRAVER_BLACKLIST = name("engraver_blacklist");
    }

    public static class Items{
        private static TagKey<Item> name(String name) {
            return TagKey.create(Registries.ITEM, TinkersAdvanced.getLocation(name));
        }
        public static final TagKey<Item> MODIFIABLE_FUEL_LENS = name("modifiable/fuel_lens");
    }
}
