package io.github.tt432.kitchenkarrot.dependencies.jei.category;

import io.github.tt432.kitchenkarrot.Kitchenkarrot;
import io.github.tt432.kitchenkarrot.dependencies.jei.JeiPlugin;
import io.github.tt432.kitchenkarrot.item.ModItems;
import io.github.tt432.kitchenkarrot.recipes.recipe.AirCompressorRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.Objects;

/**
 * @author DustW
 **/
public class AirCompressorRecipeCategory extends BaseRecipeCategory<AirCompressorRecipe> {
    protected static final ResourceLocation BACKGROUND =
            new ResourceLocation(Kitchenkarrot.MOD_ID, "textures/gui/air_compressor.png");

    public AirCompressorRecipeCategory(IGuiHelper helper) {
        super(JeiPlugin.AIR_COMPRESSOR,
                helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModItems.AIR_COMPRESSOR.get())),
                helper.createDrawable(BACKGROUND, 0, 0, 176, 166 - 90));
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, AirCompressorRecipe recipe, IFocusGroup focuses) {
        var ingredient = recipe.getIngredient();

        builder.addSlot(RecipeIngredientRole.INPUT, 32 + 1, 25 + 1).addIngredients(ingredient.get(0));
        builder.addSlot(RecipeIngredientRole.INPUT, 50 + 1, 25 + 1).addIngredients(ingredient.get(1));
        builder.addSlot(RecipeIngredientRole.INPUT, 32 + 1, 43 + 1).addIngredients(ingredient.get(2));
        builder.addSlot(RecipeIngredientRole.INPUT, 50 + 1, 43 + 1).addIngredients(ingredient.get(3));

        var container = Objects.requireNonNullElse(recipe.getContainer(), Ingredient.EMPTY);
        builder.addSlot(RecipeIngredientRole.INPUT, 81 + 1, 14 + 1).addIngredients(container);

        builder.addSlot(RecipeIngredientRole.OUTPUT, 120 + 1, 34 + 1).addItemStack(recipe.getResultItem());
    }
}
