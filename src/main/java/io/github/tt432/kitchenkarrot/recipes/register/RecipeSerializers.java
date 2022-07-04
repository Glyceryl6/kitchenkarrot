package io.github.tt432.kitchenkarrot.recipes.register;

import io.github.tt432.kitchenkarrot.recipes.base.BaseSerializer;
import io.github.tt432.kitchenkarrot.recipes.recipe.AirCompressorRecipe;
import io.github.tt432.kitchenkarrot.recipes.recipe.BrewingBarrelRecipe;
import io.github.tt432.kitchenkarrot.recipes.recipe.CocktailRecipe;
import io.github.tt432.kitchenkarrot.recipes.recipe.PlateRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * @author DustW
 **/
public class RecipeSerializers {
    private static final DeferredRegister<RecipeSerializer<?>> SERIALIZER =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, RecipeManager.MOD_ID);

    public static final RegistryObject<BaseSerializer<?>> COCKTAIL =
            SERIALIZER.register("cocktail", () -> new BaseSerializer<>(CocktailRecipe.class));

    public static final RegistryObject<BaseSerializer<?>> AIR_COMPRESSOR =
            SERIALIZER.register("air_compressing", () -> new BaseSerializer<>(AirCompressorRecipe.class));

    public static final RegistryObject<BaseSerializer<?>> BREWING_BARREL =
            SERIALIZER.register("brewing_barrel", () -> new BaseSerializer<>(BrewingBarrelRecipe.class));

    public static final RegistryObject<BaseSerializer<?>> PLATE =
            SERIALIZER.register("plate", () -> new BaseSerializer<>(PlateRecipe.class));


    public static void register(IEventBus bus) {
        SERIALIZER.register(bus);
    }
}
