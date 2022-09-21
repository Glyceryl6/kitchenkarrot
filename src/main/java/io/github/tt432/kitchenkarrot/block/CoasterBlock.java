package io.github.tt432.kitchenkarrot.block;

import io.github.tt432.kitchenkarrot.blockentity.CoasterBlockEntity;
import io.github.tt432.kitchenkarrot.blockentity.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.items.CapabilityItemHandler;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author DustW
 **/
public class CoasterBlock extends FacingEntityBlock<CoasterBlockEntity> {
    static {
        var part1 = Block.box(4, 0, 3, 4 + 2, 1, 16 - 3);
        var part2 = Block.box(3, 0, 4, 16 - 3, 1, 4 + 2);
        var part3 = Block.box(16 - 4 - 2, 0, 3, 16 - 4, 1, 16 - 3);
        var part4 = Block.box(3, 0, 16 - 4 - 2, 16 - 3, 1, 16 - 4);

        SHAPE = Shapes.or(part1, part2, part3, part4);
    }

    public static final VoxelShape SHAPE;

    public CoasterBlock(Properties p_49795_) {
        super(p_49795_);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
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
