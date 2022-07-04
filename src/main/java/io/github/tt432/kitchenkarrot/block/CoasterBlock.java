package io.github.tt432.kitchenkarrot.block;

import io.github.tt432.kitchenkarrot.blockentity.CoasterBlockEntity;
import io.github.tt432.kitchenkarrot.blockentity.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.items.CapabilityItemHandler;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author DustW
 **/
public class CoasterBlock extends FacingEntityBlock<CoasterBlockEntity> {
    public CoasterBlock(Properties p_49795_) {
        super(p_49795_);
    }

    @Override
    public BlockEntityType<CoasterBlockEntity> getBlockEntity() {
        return ModBlockEntities.COASTER.get();
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (pLevel.isClientSide) {
            return InteractionResult.SUCCESS;
        }

        AtomicBoolean success = new AtomicBoolean(false);

        pLevel.getBlockEntity(pPos).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(handler -> {
            if (handler.getStackInSlot(0).isEmpty() && !pPlayer.getItemInHand(pHand).isEmpty()) {
                handler.insertItem(0, pPlayer.getItemInHand(pHand), false);
                pPlayer.setItemInHand(pHand, ItemStack.EMPTY);
                success.set(true);
            }
            else if (!handler.getStackInSlot(0).isEmpty()) {
                var item = handler.extractItem(0, 64, false);

                if (!pPlayer.addItem(item)) {
                    pPlayer.drop(item, true);
                }

                success.set(true);
            }
        });

        if (success.get()) {
            return InteractionResult.SUCCESS;
        }

        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }
}
