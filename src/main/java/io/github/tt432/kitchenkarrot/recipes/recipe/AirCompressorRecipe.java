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
public class AirCompressorRecipe extends BaseRecipe<AirCompressorRecipe> {
    @Expose
    NonNullList<Ingredient> ingredient = NonNullList.withSize(4, Ingredient.EMPTY);
    @Expose
    @SerializedName("craftingtime")
    int craftingTime;
    @Expose
    Ingredient container;
    @Expose
    ItemStack result;

    @Override
    public boolean matches(List<ItemStack> inputs) {
        return RecipeMatcher.findMatches(inputs, ingredient) != null;
    }

    public NonNullList<Ingredient> getIngredient() {
        return ingredient;
    }

    public boolean testContainer(ItemStack stack) {
        return container == null || container.test(stack);
    }

    public Ingredient getContainer() {
        return container;
    }

    @Override
    public ItemStack getResultItem() {
        return result.copy();
    }

    public int getCraftingTime() {
        return craftingTime;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeSerializers.AIR_COMPRESSOR.get();
    }

    @Override
    public RecipeType<?> getType() {
        return RecipeTypes.AIR_COMPRESSOR.get();
    }
}
