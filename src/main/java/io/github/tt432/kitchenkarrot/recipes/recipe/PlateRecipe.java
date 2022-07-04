package io.github.tt432.kitchenkarrot.recipes.recipe;

import com.google.gson.annotations.Expose;
import io.github.tt432.kitchenkarrot.recipes.base.BaseRecipe;
import io.github.tt432.kitchenkarrot.recipes.register.RecipeSerializers;
import io.github.tt432.kitchenkarrot.recipes.register.RecipeTypes;
import io.github.tt432.kitchenkarrot.tag.ModItemTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.List;

/**
 * @author DustW
 **/
public class PlateRecipe extends BaseRecipe<PlateRecipe> {

    @Expose Input item;
    @Expose @Nullable Output cut;

    static class Input {
        @Expose String item;
        @Expose int maxCount;

        Item cache;

        Item item() {
            return cache == null ? cache = ForgeRegistries.ITEMS.getValue(new ResourceLocation(item)) : cache;
        }
    }

    static class Output {
        @Expose @Nullable Ingredient tool;
        @Expose String out;

        Ingredient defaultIngredient = Ingredient.of(ModItemTags.KNIFE_ITEM);

        public Ingredient tool() {
            return tool == null || tool.isEmpty() ? defaultIngredient : tool;
        }

        Item cache;

        Item out() {
            return cache == null ? cache = ForgeRegistries.ITEMS.getValue(new ResourceLocation(out)) : cache;
        }
    }

    public boolean canCut(ItemStack tool, ItemStack input) {
        return cut != null && cut.tool().test(tool) && item.item() == input.getItem() && input.getCount() >= item.maxCount;
    }

    public int getMax() {
        return item.maxCount;
    }

    public ItemStack getMaxStack() {
        return new ItemStack(item.item(), item.maxCount);
    }

    @Override
    public boolean matches(List<ItemStack> inputs) {
        return item.item() == inputs.get(0).getItem();
    }

    @Override
    public ItemStack getResultItem() {
        return cut != null && cut.out != null ? new ItemStack(cut.out()) : ItemStack.EMPTY;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeSerializers.PLATE.get();
    }

    @Override
    public RecipeType<?> getType() {
        return RecipeTypes.PLATE.get();
    }
}
