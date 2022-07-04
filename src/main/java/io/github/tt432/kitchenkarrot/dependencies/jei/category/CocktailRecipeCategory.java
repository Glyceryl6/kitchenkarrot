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
            new ResourceLocation(Kitchenkarrot.MOD_ID, "textures/gui/shaker.png");

    public CocktailRecipeCategory(IGuiHelper helper) {
        super(JeiPlugin.COCKTAIL,
                helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModItems.SHAKER.get())),
                helper.createDrawable(BACKGROUND, 0, 0, 176, 166 - 90));
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, CocktailRecipe recipe, IFocusGroup focuses) {
        var ingredients = recipe.getContent().getRecipe();

        builder.addSlot(RecipeIngredientRole.INPUT, 110 + 1, 25 + 1).addIngredients(ingredients.get(0));
        builder.addSlot(RecipeIngredientRole.INPUT, 128 + 1, 25 + 1).addIngredients(ingredients.get(1));
        builder.addSlot(RecipeIngredientRole.INPUT, 146 + 1, 25 + 1).addIngredients(ingredients.get(2));
        builder.addSlot(RecipeIngredientRole.INPUT, 118 + 1, 43 + 1).addIngredients(ingredients.get(3));
        builder.addSlot(RecipeIngredientRole.INPUT, 136 + 1, 43 + 1).addIngredients(ingredients.get(4));

        builder.addSlot(RecipeIngredientRole.OUTPUT, 71 + 1, 33 + 1).addItemStack(recipe.getResultItem());
    }
}
