package io.github.tt432.kitchenkarrot.block;

import io.github.tt432.kitchenkarrot.blockentity.BaseBlockEntity;
import io.github.tt432.kitchenkarrot.blockentity.MenuBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Containers;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

/**
 * @author DustW
 **/
public abstract class ModBaseEntityBlock<T extends BaseBlockEntity> extends BaseEntityBlock {

    protected ModBaseEntityBlock(Properties p_49224_) {
        super(p_49224_);
    }

    public abstract BlockEntityType<T> getBlockEntity();

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return getBlockEntity().create(pPos, pState);
    }

    @Override
    public List<ItemStack> getDrops(BlockState pState, LootContext.Builder pBuilder) {
        return Collections.singletonList(new ItemStack(this));
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (pLevel.getBlockEntity(pPos) instanceof MenuBlockEntity kk) {
            for (ItemStack drop : kk.drops()) {
                Containers.dropItemStack(pLevel, pPos.getX(), pPos.getY(), pPos.getZ(), drop);
            }
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }

    @Nullable
    @Override
    public <F extends BlockEntity> BlockEntityTicker<F> getTicker(Level pLevel, BlockState pState, BlockEntityType<F> pBlockEntityType) {
        return createTickerHelper(pBlockEntityType, getBlockEntity(), BaseBlockEntity::tick);
    }
}
