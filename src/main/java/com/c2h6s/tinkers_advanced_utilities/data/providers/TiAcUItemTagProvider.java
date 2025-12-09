package com.c2h6s.tinkers_advanced_utilities.data.providers;

import com.c2h6s.tinkers_advanced_utilities.data.TiAcUTagkeys;
import com.c2h6s.tinkers_advanced_utilities.init.TiAcUItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;
import slimeknights.tconstruct.common.TinkerTags;

import java.util.concurrent.CompletableFuture;

public class TiAcUItemTagProvider extends ItemTagsProvider {
    public TiAcUItemTagProvider(PackOutput p_275343_, CompletableFuture<HolderLookup.Provider> p_275729_, CompletableFuture<TagLookup<Block>> p_275322_) {
        super(p_275343_, p_275729_, p_275322_);
    }

    @Override
    protected void addTags(HolderLookup.Provider p_256380_) {
        TiAcUItems.SIMPLE_LENS_TAGS.forEach((obj,tag)->
                tag.forEach(tagKey-> this.tag(tagKey).addOptional(obj.getId())));
        TiAcUItems.SIMPLE_LENS_MAP.keySet().stream().map(RegistryObject::getId).forEach(id->{
            tag(TinkerTags.Items.MODIFIABLE).addOptional(id);
            tag(TinkerTags.Items.BONUS_SLOTS).addOptional(id);
            tag(TiAcUTagkeys.Items.MODIFIABLE_FUEL_LENS).addOptional(id);
        });
    }
}
