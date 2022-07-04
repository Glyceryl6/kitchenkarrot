package io.github.tt432.kitchenkarrot.recipes.register;

import io.github.tt432.kitchenkarrot.Kitchenkarrot;
import io.github.tt432.kitchenkarrot.recipes.base.BaseRecipe;
import io.github.tt432.kitchenkarrot.recipes.recipe.AirCompressorRecipe;
import io.github.tt432.kitchenkarrot.recipes.recipe.CocktailRecipe;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.IEventBus;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author DustW
 **/
public class RecipeManager {
    public static final String MOD_ID = Kitchenkarrot.MOD_ID;

    public static <T extends BaseRecipe<T>> List<T> getRecipe(Level level, RecipeType<T> type, List<ItemStack> itemStacks) {
        return level.getRecipeManager().getAllRecipesFor(type).stream().filter(s -> s.matches(itemStacks)).collect(Collectors.toList());
    }

    public static List<CocktailRecipe> getCocktailRecipes(Level level) {
        return level.getRecipeManager().getAllRecipesFor(RecipeTypes.COCKTAIL.get());
    }

    public static List<AirCompressorRecipe> getAirCompressorRecipe(Level level) {
        return level.getRecipeManager().getAllRecipesFor(RecipeTypes.AIR_COMPRESSOR.get());
    }

    public static void register(IEventBus bus) {
        RecipeTypes.register(bus);
        RecipeSerializers.register(bus);
    }
}
