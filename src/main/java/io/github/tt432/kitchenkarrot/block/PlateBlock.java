package io.github.tt432.kitchenkarrot.block;

import io.github.tt432.kitchenkarrot.blockentity.ModBlockEntities;
import io.github.tt432.kitchenkarrot.blockentity.PlateBlockEntity;
import io.github.tt432.kitchenkarrot.recipes.recipe.PlateRecipe;
import io.github.tt432.kitchenkarrot.recipes.register.RecipeTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author DustW
 **/

@SuppressWarnings("deprecation")
@ParametersAreNonnullByDefault
public class PlateBlock extends FacingEntityBlock<PlateBlockEntity> {

    public PlateBlock(Properties properties) {
        super(properties);
    }

    @Override
    public BlockEntityType<PlateBlockEntity> getBlockEntity() {
        return ModBlockEntities.PLATE.get();
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.INVISIBLE;
    }

    @NotNull
    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        }

        AtomicBoolean success = new AtomicBoolean(false);
        BlockEntity blockEntity = level.getBlockEntity(pos);

        if (blockEntity != null) {
            blockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(handler -> {
                ItemStack heldItem = player.getItemInHand(hand);
                ItemStack input = handler.getStackInSlot(0);
                if (player.isCrouching()) {
                    if (heldItem.isEmpty()) {
                        ItemStack stack = new ItemStack(this);
                        blockEntity.saveToItem(stack);
                        setPlate(stack, input);
                        //??????????????????????????????????????????????????????"?????????XXX"
                        if (stack.getOrCreateTag().contains("plate_type") && !input.is(Items.AIR)) {
                            String inputName = input.getDisplayName().getString().replace("[", "").replace("]", "");
                            stack.setHoverName((new TranslatableComponent("info.kitchenkarrot.dished", inputName)).setStyle(Style.EMPTY.withItalic(false)));
                        }
                        player.setItemInHand(hand, stack);
                        level.setBlock(pos, Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL_IMMEDIATE);
                    }
                } else {
                    boolean isEmpty = input.isEmpty();
                    if (isEmpty && !heldItem.isEmpty()) {
                        if (intoPlate(level, heldItem, handler)) {
                            success.set(true);
                        }
                    } else if (!isEmpty) {
                        if (outPlate(level, player, handler, input, heldItem)) {
                            success.set(true);
                        }
                    }
                }
            });
        }

        if (success.get()) {
            return InteractionResult.SUCCESS;
        }

        return super.use(state, level, pos, player, hand, hit);
    }

    boolean outPlate(Level level, Player player, IItemHandler handler, ItemStack input, ItemStack heldItem) {
        Optional<PlateRecipe> recipe = level.getRecipeManager().getAllRecipesFor(RecipeTypes.PLATE.get())
                .stream().filter(r -> r.matches(Collections.singletonList(input)) && r.canCut(heldItem, input)).findFirst();

        AtomicBoolean result = new AtomicBoolean(false);

        recipe.ifPresent(r -> {
            if (giveRecipeResult(level, r, handler)) {
                result.set(true);
            }
        });

        if (recipe.isEmpty()) {
            ItemStack item = handler.extractItem(0, 64, false);

            //??????????????????????????????????????????????????????????????????
            ItemEntity itemEntity = new ItemEntity(level, player.getX(), player.getY(), player.getZ(), item);
            level.addFreshEntity(itemEntity);

            result.set(true);
        }

        return result.get();
    }

    boolean intoPlate(Level level, ItemStack heldItem, IItemHandler handler) {
        Optional<PlateRecipe> recipe = level.getRecipeManager().getAllRecipesFor(RecipeTypes.PLATE.get())
                .stream().filter(r -> r.matches(Collections.singletonList(heldItem))).findFirst();

        AtomicBoolean result = new AtomicBoolean(false);

        recipe.ifPresent(r -> {
            ItemStack maxStack = heldItem.split(r.getMax());
            handler.insertItem(0, maxStack, false);
            result.set(true);
        });

        return result.get();
    }

    boolean giveRecipeResult(Level level, PlateRecipe recipe, IItemHandler handler) {
        Optional<PlateRecipe> outputRecipe = level.getRecipeManager().getAllRecipesFor(RecipeTypes.PLATE.get())
                .stream().filter(or -> or.matches(Collections.singletonList(recipe.getResultItem()))).findFirst();

        AtomicBoolean result = new AtomicBoolean(false);

        outputRecipe.ifPresent(or -> {
            handler.extractItem(0, 64, false);
            handler.insertItem(0, or.getMaxStack(), false);
            result.set(true);
        });

        return result.get();
    }

    public static void setPlate(ItemStack self, ItemStack content) {
        self.getOrCreateTag().putInt("plate_amount", content.getCount());
        if (content.getItem().getRegistryName() != null) {
            self.getOrCreateTag().putString("plate_type", content.getItem().getRegistryName().toString());
        }
    }

}
