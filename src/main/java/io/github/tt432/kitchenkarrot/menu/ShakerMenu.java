package io.github.tt432.kitchenkarrot.menu;

import io.github.tt432.kitchenkarrot.item.CocktailItem;
import io.github.tt432.kitchenkarrot.item.ShakerItem;
import io.github.tt432.kitchenkarrot.menu.base.KKMenu;
import io.github.tt432.kitchenkarrot.menu.reg.ModMenuTypes;
import io.github.tt432.kitchenkarrot.menu.slot.KKResultSlot;
import io.github.tt432.kitchenkarrot.recipes.register.RecipeManager;
import io.github.tt432.kitchenkarrot.sound.ModSoundEvents;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @author DustW
 **/
public class ShakerMenu extends KKMenu {
    ItemStack itemStack;

    public ShakerMenu(int pContainerId, Inventory inventory) {
        super(ModMenuTypes.SHAKER.get(), pContainerId, inventory);

        itemStack = inventory.getSelected();

        if (!(itemStack.getItem() instanceof ShakerItem)) {
            removed(inventory.player);
            return;
        }

        addSlots();
        finishRecipe(inventory.player);
    }

    private void finishRecipe(Player player) {
        if (ShakerItem.getFinish(itemStack)) {
            itemStack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(handler -> {
                if (player.level.isClientSide) {
                    return;
                }

                var list = getInputs(handler);

                var recipe = RecipeManager.getCocktailRecipes(inventory.player.level).stream()
                    .filter(r -> r.matches(list)).findFirst();

                var recipeResult = CocktailItem.unknownCocktail();

                if (recipe.isPresent()) {
                    recipeResult = recipe.get().getResultItem();
                }

                if (list.stream().anyMatch(ItemStack::isEmpty)) {
                    return;
                }

                var result = handler.insertItem(11, recipeResult, false);

                if (!result.isEmpty()) {
                    inventory.player.drop(result, true);
                }

                for (int i = 0; i < 5; i++) {
                    handler.extractItem(i, 1, false);
                }

                slotChanged(handler);
            });

            ShakerItem.setFinish(itemStack, false);
        }
    }

    List<ItemStack> getInputs(IItemHandler handler) {
        var list = new ArrayList<ItemStack>();

        for (int i = 0; i < 5; i++) {
            list.add(handler.getStackInSlot(i));
        }

        return list;
    }

    void slotChanged(IItemHandler handler) {
        if (!handler.getStackInSlot(11).isEmpty()) {
            ShakerItem.setRecipeTime(itemStack, 0);
            return;
        }

        var list = getInputs(handler);

        var recipe = RecipeManager.getCocktailRecipes(inventory.player.level)
                .stream().filter(r -> r.matches(list)).findFirst();
        if (recipe.isPresent()) {
            ShakerItem.setRecipeTime(itemStack, recipe.get().getContent().getCraftingTime());
        }
        else {
            if (list.stream().anyMatch(ItemStack::isEmpty)) {
                ShakerItem.setRecipeTime(itemStack, 0);
            }
            else {
                ShakerItem.setRecipeTime(itemStack, 60);
            }
        }
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        if (index == slots.size() - 1) {
            sound();
        }
        return super.quickMoveStack(player, index);
    }

    @Override
    protected Slot addSlot(IItemHandler handler, int index, int x, int y) {
        return addSlot(new SlotItemHandler(handler, index, x, y) {
            @Override
            public void setChanged() {
                super.setChanged();
                slotChanged(handler);
            }
        });
    }

    protected void sound() {
        var player = inventory.player;

        if (player.level.isClientSide) {
            player.playSound(ModSoundEvents.SHAKER_COCKTAIL.get(), 0.5F,
                    player.getRandom().nextFloat() * 0.1F + 0.9F);
        }
    }

    @Override
    protected Slot addResultSlot(IItemHandler handler, int index, int x, int y) {
        return addSlot(new KKResultSlot(handler, index, x, y) {
            @Override
            public void set(@NotNull ItemStack stack) {
                if (stack.isEmpty()) {
                    sound();
                }

                super.set(stack);
            }

            @Override
            public void setChanged() {
                super.setChanged();
                slotChanged(handler);
            }
        });
    }

    void addSlots() {
        itemStack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
            addSlot(h, 0, 110 + 1, 25 + 1);
            addSlot(h, 1, 128 + 1, 25 + 1);
            addSlot(h, 2, 146 + 1, 25 + 1);
            addSlot(h, 3, 118 + 1, 43 + 1);
            addSlot(h, 4, 136 + 1, 43 + 1);

            addSlot(h, 5, 12 + 1, 13 + 1);
            addSlot(h, 6, 30 + 1, 13 + 1);
            addSlot(h, 7, 12 + 1, 31 + 1);
            addSlot(h, 8, 30 + 1, 31 + 1);

            addSlot(h, 9, 12 + 1, 49 + 1);
            addSlot(h, 10, 30 + 1, 49 + 1);

            addResultSlot(h, 11, 71 + 1, 33 + 1);
        });
    }

    @Override
    public void removed(Player pPlayer) {
        super.removed(pPlayer);

        if (pPlayer.level.isClientSide) {
            pPlayer.playSound(ModSoundEvents.SHAKER_CLOSE.get(), 0.5F,
                    pPlayer.getRandom().nextFloat() * 0.1F + 0.9F);
        }
    }
}
