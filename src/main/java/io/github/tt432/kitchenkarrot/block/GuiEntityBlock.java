package io.github.tt432.kitchenkarrot.block;

import io.github.tt432.kitchenkarrot.blockentity.BaseBlockEntity;
import io.github.tt432.kitchenkarrot.blockentity.MenuBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;

/**
 * @author DustW
 **/
public abstract class GuiEntityBlock<T extends BaseBlockEntity> extends ModBaseEntityBlock<T> {

    protected GuiEntityBlock(Properties p_49224_) {
        super(p_49224_);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (pLevel.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            var be = pLevel.getBlockEntity(pPos);

            if (be instanceof MenuBlockEntity kk) {
                NetworkHooks.openGui((ServerPlayer) pPlayer, kk, be.getBlockPos());
                kk.forceOnce();
            }
            return InteractionResult.CONSUME;
        }
    }
}
