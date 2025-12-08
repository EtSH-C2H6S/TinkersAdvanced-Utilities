package com.c2h6s.tinkers_advanced_utilities.data.providers;

import com.c2h6s.tinkers_advanced.core.init.TiAcCrItem;
import com.c2h6s.tinkers_advanced_utilities.TinkersAdvancedUtilities;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class TiAcUBlockStateProvider extends BlockStateProvider {
    public TiAcUBlockStateProvider(PackOutput output, String modId, ExistingFileHelper exFileHelper) {
        super(output, modId, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
//        for (RegistryObject<BlockItem> object: TiAcCrItem.getListSimpleBlock(TinkersAdvancedUtilities.MODID)){
//            Block block =object.get().getBlock();
//            ModelFile file = cubeAll(block);
//            simpleBlock(block,file);
//        }
    }
}
