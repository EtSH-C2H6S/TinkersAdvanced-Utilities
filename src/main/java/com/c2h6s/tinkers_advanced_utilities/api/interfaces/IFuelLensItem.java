package com.c2h6s.tinkers_advanced_utilities.api.interfaces;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.function.Predicate;

public interface IFuelLensItem {
    Predicate<ItemStack> getItemPredicate();
}
