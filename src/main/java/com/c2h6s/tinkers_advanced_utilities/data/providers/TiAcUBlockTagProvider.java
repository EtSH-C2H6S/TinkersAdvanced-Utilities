package com.c2h6s.tinkers_advanced_utilities.data.providers;

import com.c2h6s.tinkers_advanced_utilities.TinkersAdvancedUtilities;
import com.c2h6s.tinkers_advanced_utilities.init.TiAcUBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class TiAcUBlockTagProvider extends BlockTagsProvider {
    public TiAcUBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, TinkersAdvancedUtilities.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(Tiers.WOOD.getTag()).add(TiAcUBlocks.EXCHANGER.get());
        tag(Tiers.IRON.getTag()).add(
                TiAcUBlocks.ROSE_GOLD_FAUCET.get(),
                TiAcUBlocks.ROSE_GOLD_TABLE.get(),
                TiAcUBlocks.ROSE_GOLD_BASIN.get(),
                TiAcUBlocks.HEPATIZON_BASIN.get(),
                TiAcUBlocks.HEPATIZON_TABLE.get(),
                TiAcUBlocks.HEPATIZON_FAUCET.get(),
                TiAcUBlocks.ADVANCED_ALLOYER.get(),
                TiAcUBlocks.FUEL_ENGRAVER.get()
        );
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(TiAcUBlocks.EXCHANGER.get())
                .add(TiAcUBlocks.ROSE_GOLD_FAUCET.get())
                .add(TiAcUBlocks.ROSE_GOLD_TABLE.get())
                .add(TiAcUBlocks.ROSE_GOLD_BASIN.get())
                .add(TiAcUBlocks.HEPATIZON_BASIN.get())
                .add(TiAcUBlocks.HEPATIZON_TABLE.get())
                .add(TiAcUBlocks.HEPATIZON_FAUCET.get())
                .add(TiAcUBlocks.ADVANCED_ALLOYER.get())
                .add(TiAcUBlocks.FUEL_ENGRAVER.get())
        ;
    }
}
