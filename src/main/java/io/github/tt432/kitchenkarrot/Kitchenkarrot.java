package io.github.tt432.kitchenkarrot;

import io.github.tt432.kitchenkarrot.block.ModBlocks;
import io.github.tt432.kitchenkarrot.blockentity.ModBlockEntities;
import io.github.tt432.kitchenkarrot.entity.ModEntitys;
import io.github.tt432.kitchenkarrot.item.ModBlockItems;
import io.github.tt432.kitchenkarrot.item.ModItems;
import io.github.tt432.kitchenkarrot.menu.reg.ModMenuTypes;
import io.github.tt432.kitchenkarrot.net.ModNetManager;
import io.github.tt432.kitchenkarrot.recipes.register.RecipeManager;
import io.github.tt432.kitchenkarrot.sound.ModSoundEvents;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

/**
 * @author DustW
 */
@Mod(Kitchenkarrot.MOD_ID)
public class Kitchenkarrot {
    public static final String MOD_ID = "kitchenkarrot";

    public Kitchenkarrot() {

        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ModBlocks.BLOCKS.register(bus);
        ModItems.ITEMS.register(bus);
        ModBlockItems.BLOCK_ITEMS.register(bus);
        ModMenuTypes.MENUS.register(bus);
        ModBlockEntities.BLOCK_ENTITIES.register(bus);
        ModSoundEvents.SOUNDS.register(bus);
        ModEntitys.ENTITYS.register(bus);

        RecipeManager.register(bus);
        ModNetManager.register();
    }
}
