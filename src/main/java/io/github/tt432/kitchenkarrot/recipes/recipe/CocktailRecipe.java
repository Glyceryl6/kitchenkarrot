package io.github.tt432.kitchenkarrot.recipes.recipe;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import io.github.tt432.kitchenkarrot.item.CocktailItem;
import io.github.tt432.kitchenkarrot.item.ModItems;
import io.github.tt432.kitchenkarrot.recipes.base.BaseRecipe;
import io.github.tt432.kitchenkarrot.recipes.object.EffectStack;
import io.github.tt432.kitchenkarrot.recipes.register.RecipeSerializers;
import io.github.tt432.kitchenkarrot.recipes.register.RecipeTypes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.common.util.RecipeMatcher;

import java.util.ArrayList;
import java.util.List;

/**
 * @author DustW
 **/
public class CocktailRecipe extends BaseRecipe<CocktailRecipe> {
    @Override
    public boolean matches(List<ItemStack> inputs) {
        return RecipeMatcher.findMatches(inputs, content.recipe) != null;
    }

    ItemStack result;

    @Override
    public ItemStack getResultItem() {
        if (result == null) {
            result = new ItemStack(ModItems.COCKTAIL.get());
            CocktailItem.setCocktail(result, getId());
        }
        return result.copy();
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeSerializers.COCKTAIL.get();
    }

    @Override
    public RecipeType<?> getType() {
        return RecipeTypes.COCKTAIL.get();
    }

    @Expose
    public String author;
    @Expose
    public Content content;

    public static class Content {
        @Expose
        List<Ingredient> recipe;
        @Expose
        @SerializedName("craftingtime")
        int craftingTime;
        @Expose
        public int hunger;
        @Expose
        public int saturation;
        @Expose
        List<EffectStack> effect;

        public int getCraftingTime() {
            return craftingTime;
        }

        public List<Ingredient> getRecipe() {
            return recipe;
        }

        public List<EffectStack> getEffect() {
            return effect == null ? new ArrayList<>() : effect;
        }
    }

    public Content getContent() {
        return content;
    }
}
