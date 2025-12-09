package com.c2h6s.tinkers_advanced_utilities.content.block;

import com.c2h6s.tinkers_advanced_utilities.content.block.blockEntity.FuelEngraverBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import slimeknights.mantle.block.InventoryBlock;
import slimeknights.mantle.util.BlockEntityHelper;
import slimeknights.tconstruct.library.utils.NBTTags;
import slimeknights.tconstruct.smeltery.block.component.SearedTankBlock;
import slimeknights.tconstruct.smeltery.block.entity.component.TankBlockEntity;
import slimeknights.tconstruct.tables.block.entity.table.TinkerStationBlockEntity;

import javax.annotation.Nullable;

import java.util.List;

import static slimeknights.tconstruct.smeltery.block.component.SearedTankBlock.LIGHT;

public class FuelEngraverBlock extends InventoryBlock implements TankBlockEntity.ITankBlock, EntityBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    public FuelEngraverBlock(Properties properties) {
        super(properties);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return SearedTankBlock.setLightLevel(defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite()), context);
    }

    @Deprecated
    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Deprecated
    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, LIGHT);
    }


    @Override
    protected boolean openGui(Player player, Level world, BlockPos pos) {
        return false;
    }

    @Deprecated
    @Override
    public float getShadeBrightness(BlockState state, BlockGetter worldIn, BlockPos pos) {
        return 1.0F;
    }

    @Override
    @Nullable
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new FuelEngraverBlockEntity(pPos, pState, this);
    }

    @Deprecated
    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (world.getBlockEntity(pos) instanceof FuelEngraverBlockEntity entity) {
            if (isInPlace(world,pos))
                entity.interact(player, hand, hit.getLocation().y - pos.getY() > 0.5);
            else if (!world.isClientSide) player.displayClientMessage(Component.translatable("message.tinkers_advanced.no_tinkers_station"), true);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.FAIL;
    }

    @Override
    public List<ItemStack> getDrops(BlockState p_287732_, LootParams.Builder p_287596_) {
        return List.of(new ItemStack(this.asItem())) ;
    }

    public static boolean isInPlace(Level world, BlockPos pos){
        return world.getBlockEntity(pos.below(2)) instanceof TinkerStationBlockEntity;
    }

    @Override
    public void setPlacedBy(Level worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        CompoundTag nbt = stack.getTag();
        if (nbt != null && worldIn.getBlockEntity(pos) instanceof FuelEngraverBlockEntity entity) {
            entity.updateTank(nbt.getCompound(NBTTags.TANK));
        }

        super.setPlacedBy(worldIn, pos, state, placer, stack);
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter world, BlockPos pos, Player player) {
        ItemStack stack = new ItemStack(this);
        BlockEntityHelper.get(FuelEngraverBlockEntity.class, world, pos).ifPresent(te -> te.setTankTag(stack));
        return stack;
    }

    @Override
    public int getCapacity() {
        return FuelEngraverBlockEntity.CFG_CAPACITY;
    }
}