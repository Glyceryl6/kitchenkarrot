package io.github.tt432.kitchenkarrot.block;

import io.github.tt432.kitchenkarrot.blockentity.ModBlockEntities;
import io.github.tt432.kitchenkarrot.blockentity.PlateBlockEntity;
import io.github.tt432.kitchenkarrot.recipes.recipe.PlateRecipe;
import io.github.tt432.kitchenkarrot.recipes.register.RecipeTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import java.util.Collections;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author DustW
 **/
public class PlateBlock extends FacingEntityBlock<PlateBlockEntity> {
    public PlateBlock(Properties p_49795_) {
        super(p_49795_);
    }

    @Override
    public BlockEntityType<PlateBlockEntity> getBlockEntity() {
        return ModBlockEntities.PLATE.get();
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.INVISIBLE;
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (pLevel.isClientSide) {
            return InteractionResult.SUCCESS;
        }

        AtomicBoolean success = new AtomicBoolean(false);
        var blockEntity = pLevel.getBlockEntity(pPos);

        if (blockEntity != null) {
            blockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(handler -> {
                var heldItem = pPlayer.getItemInHand(pHand);
                var input = handler.getStackInSlot(0);

                if (pPlayer.isCrouching()) {
                    if (heldItem.isEmpty()) {
                        var stack = new ItemStack(this);
                        blockEntity.saveToItem(stack);
                        setPlate(stack, input);
                        pPlayer.setItemInHand(pHand, stack);

                        pLevel.setBlock(pPos, Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL_IMMEDIATE);
                    }
                } else {
                    var isEmpty = input.isEmpty();

                    if (isEmpty && !heldItem.isEmpty()) {
                        if (intoPlate(pLevel, heldItem, handler)) {
                            success.set(true);
                        }
                    } else if (!isEmpty) {
                        if (outPlate(pLevel, pPlayer, handler, input, heldItem)) {
                            success.set(true);
                        }
                    }
                }
            });
        }

        if (success.get()) {
            return InteractionResult.SUCCESS;
        }

        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }

    boolean outPlate(Level pLevel, Player pPlayer, IItemHandler handler, ItemStack input, ItemStack heldItem) {
        var recipe = pLevel.getRecipeManager().getAllRecipesFor(RecipeTypes.PLATE.get())
                .stream()
                .filter(r -> r.matches(Collections.singletonList(input)) && r.canCut(heldItem, input))
                .findFirst();

        AtomicBoolean result = new AtomicBoolean(false);

        recipe.ifPresent(r -> {
            if (giveRecipeResult(pLevel, r, handler)) {
                result.set(true);
            }
        });

        if (recipe.isEmpty()) {
            var item = handler.extractItem(0, 64, false);

            if (!pPlayer.addItem(item)) {
                pPlayer.drop(item, true);
            }

            result.set(true);
        }

        return result.get();
    }

    boolean intoPlate(Level pLevel, ItemStack heldItem, IItemHandler handler) {
        var recipe = pLevel.getRecipeManager().getAllRecipesFor(RecipeTypes.PLATE.get())
                .stream().filter(r -> r.matches(Collections.singletonList(heldItem))).findFirst();

        AtomicBoolean result = new AtomicBoolean(false);

        recipe.ifPresent(r -> {
            var maxStack = heldItem.split(r.getMax());
            handler.insertItem(0, maxStack, false);
            result.set(true);
        });

        return result.get();
    }

    boolean giveRecipeResult(Level pLevel, PlateRecipe recipe, IItemHandler handler) {
        var outputRecipe = pLevel.getRecipeManager().getAllRecipesFor(RecipeTypes.PLATE.get())
                .stream()
                .filter(or -> or.matches(Collections.singletonList(recipe.getResultItem())))
                .findFirst();

        AtomicBoolean result = new AtomicBoolean(false);

        outputRecipe.ifPresent(or -> {
            handler.extractItem(0, 64, false);
            handler.insertItem(0, or.getMaxStack(), false);
            result.set(true);
        });

        return result.get();
    }

    public static void setPlate(ItemStack self, ItemStack content) {
        self.getOrCreateTag().putString("plate_type", content.getItem().getRegistryName().toString());
        self.getOrCreateTag().putInt("plate_amount", content.getCount());
    }
}
