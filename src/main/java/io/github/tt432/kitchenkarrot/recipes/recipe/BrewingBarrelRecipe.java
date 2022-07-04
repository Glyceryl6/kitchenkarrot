package io.github.tt432.kitchenkarrot.recipes.recipe;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import io.github.tt432.kitchenkarrot.recipes.base.BaseRecipe;
import io.github.tt432.kitchenkarrot.recipes.register.RecipeSerializers;
import io.github.tt432.kitchenkarrot.recipes.register.RecipeTypes;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.common.util.RecipeMatcher;

import java.util.List;

/**
 * @author DustW
 **/
public class BrewingBarrelRecipe extends BaseRecipe<BrewingBarrelRecipe> {
    @Expose
    ItemStack result;
    @Expose
    Content content;

    static class Content {
        @Expose
        NonNullList<Ingredient> recipe = NonNullList.withSize(6, Ingredient.EMPTY);
        @Expose
        @SerializedName("craftingtime")
        int craftingTime;
    }

    public NonNullList<Ingredient> getIngredient() {
        return content.recipe;
    }

    public int getCraftingTime() {
        return content.craftingTime;
    }

    @Override
    public boolean matches(List<ItemStack> inputs) {
        return RecipeMatcher.findMatches(inputs, getIngredient()) != null;
    }

    @Override
    public ItemStack getResultItem() {
        return result.copy();
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeSerializers.BREWING_BARREL.get();
    }

    @Override
    public RecipeType<?> getType() {
        return RecipeTypes.BREWING_BARREL.get();
    }
}
