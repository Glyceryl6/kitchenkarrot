package io.github.tt432.kitchenkarrot;

import io.github.tt432.kitchenkarrot.block.ModBlocks;
import io.github.tt432.kitchenkarrot.blockentity.ModBlockEntities;
import io.github.tt432.kitchenkarrot.menu.reg.ModMenuTypes;
import io.github.tt432.kitchenkarrot.item.ModItems;
import io.github.tt432.kitchenkarrot.net.ModNetManager;
import io.github.tt432.kitchenkarrot.recipes.register.RecipeManager;
import io.github.tt432.kitchenkarrot.sound.ModSoundEvents;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

/**
 * @author DustW
 */
@Mod(Kitchenkarrot.MOD_ID)
public class Kitchenkarrot {
    public static final String MOD_ID = "kitchenkarrot";

    public static final CreativeModeTab MAIN_TAB = new CreativeModeTab(MOD_ID + ".main") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.CARROT_SPICES.get());
        }
    };

    public static final CreativeModeTab COCKTAIL_TAB = new CreativeModeTab(MOD_ID + ".cocktail") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.SHAKER.get());
        }
    };

    public static boolean farmersdelightLoaded;
    public static boolean neapolitanLoaded;

    public Kitchenkarrot() {
        farmersdelightLoaded = ModList.get().isLoaded("farmersdelight");
        neapolitanLoaded = ModList.get().isLoaded("neapolitan");


        var bus = FMLJavaModLoadingContext.get().getModEventBus();
        ModBlocks.BLOCKS.register(bus);
        ModItems.ITEMS.register(bus);
        ModMenuTypes.MENUS.register(bus);
        ModBlockEntities.BLOCK_ENTITIES.register(bus);
        ModSoundEvents.SOUNDS.register(bus);

        RecipeManager.register(bus);
        ModNetManager.register();
    }
}
