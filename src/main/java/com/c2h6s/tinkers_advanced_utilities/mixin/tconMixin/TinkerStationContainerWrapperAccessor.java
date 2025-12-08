package com.c2h6s.tinkers_advanced_utilities.mixin.tconMixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import slimeknights.tconstruct.tables.block.entity.inventory.TinkerStationContainerWrapper;
import slimeknights.tconstruct.tables.block.entity.table.TinkerStationBlockEntity;

@Mixin(value = TinkerStationContainerWrapper.class,remap = false)
public interface TinkerStationContainerWrapperAccessor {
    @Accessor("station")
    TinkerStationBlockEntity tiacu$getStation();
}
