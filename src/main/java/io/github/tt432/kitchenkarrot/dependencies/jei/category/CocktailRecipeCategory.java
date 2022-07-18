package io.github.tt432.kitchenkarrot.dependencies.jei.category;

import io.github.tt432.kitchenkarrot.Kitchenkarrot;
import io.github.tt432.kitchenkarrot.dependencies.jei.JeiPlugin;
import io.github.tt432.kitchenkarrot.item.ModItems;
import io.github.tt432.kitchenkarrot.recipes.recipe.CocktailRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

/**
 * @author DustW
 **/
public class CocktailRecipeCategory extends BaseRecipeCategory<CocktailRecipe> {
    protected static final ResourceLocation BACKGROUND =
            new ResourceLocation(Kitchenkarrot.MOD_ID, "textures/gui/jei.png");

    public CocktailRecipeCategory(IGuiHelper helper) {
        super(JeiPlugin.COCKTAIL,
                helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModItems.SHAKER.get())),
                helper.createDrawable(BACKGROUND, 0, 0, 119, 71));
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, CocktailRecipe recipe, IFocusGroup focuses) {
        var ingredients = recipe.getContent().getRecipe();

        builder.addSlot(RecipeIngredientRole.INPUT, 49, 9).addIngredients(ingredients.get(0));
        builder.addSlot(RecipeIngredientRole.INPUT, 71, 9).addIngredients(ingredients.get(1));
        builder.addSlot(RecipeIngredientRole.INPUT, 93, 9).addIngredients(ingredients.get(2));
        builder.addSlot(RecipeIngredientRole.INPUT, 61, 32).addIngredients(ingredients.get(3));
        builder.addSlot(RecipeIngredientRole.INPUT, 83, 32).addIngredients(ingredients.get(4));

        builder.addSlot(RecipeIngredientRole.OUTPUT, 15, 32).addItemStack(recipe.getResultItem());
    }
}
