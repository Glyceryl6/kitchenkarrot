package io.github.tt432.kitchenkarrot.dependencies.jei.category;

import io.github.tt432.kitchenkarrot.Kitchenkarrot;
import io.github.tt432.kitchenkarrot.dependencies.jei.JeiPlugin;
import io.github.tt432.kitchenkarrot.item.ModItems;
import io.github.tt432.kitchenkarrot.recipes.recipe.BrewingBarrelRecipe;
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
public class BrewingBarrelRecipeCategory extends BaseRecipeCategory<BrewingBarrelRecipe> {
    public static final ResourceLocation BACKGROUND =
            new ResourceLocation(Kitchenkarrot.MOD_ID, "textures/gui/brewing_barrel.png");

    public BrewingBarrelRecipeCategory(IGuiHelper helper) {
        super(JeiPlugin.BREWING_BARREL,
                helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModItems.BREWING_BARREL.get())),
                helper.createDrawable(BACKGROUND, 0, 0, 176, 166 - 90));
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, BrewingBarrelRecipe recipe, IFocusGroup focuses) {
        var ingredients = recipe.getIngredient();

        builder.addSlot(RecipeIngredientRole.INPUT, 36 + 1, 20 + 1).addIngredients(ingredients.get(0));
        builder.addSlot(RecipeIngredientRole.INPUT, 54 + 1, 20 + 1).addIngredients(ingredients.get(1));
        builder.addSlot(RecipeIngredientRole.INPUT, 72 + 1, 20 + 1).addIngredients(ingredients.get(2));
        builder.addSlot(RecipeIngredientRole.INPUT, 36 + 1, 38 + 1).addIngredients(ingredients.get(3));
        builder.addSlot(RecipeIngredientRole.INPUT, 54 + 1, 38 + 1).addIngredients(ingredients.get(4));
        builder.addSlot(RecipeIngredientRole.INPUT, 72 + 1, 38 + 1).addIngredients(ingredients.get(5));

        builder.addSlot(RecipeIngredientRole.OUTPUT, 124 + 1, 20 + 1).addItemStack(recipe.getResultItem());
    }
}
