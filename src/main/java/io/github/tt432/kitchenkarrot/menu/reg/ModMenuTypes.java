package io.github.tt432.kitchenkarrot.menu.reg;

import io.github.tt432.kitchenkarrot.Kitchenkarrot;
import io.github.tt432.kitchenkarrot.menu.AirCompressorMenu;
import io.github.tt432.kitchenkarrot.menu.BrewingBarrelMenu;
import io.github.tt432.kitchenkarrot.menu.ShakerMenu;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * @author DustW
 **/
public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.CONTAINERS, Kitchenkarrot.MOD_ID);

    public static final RegistryObject<MenuType<AirCompressorMenu>> AIR_COMPRESSOR =
            MENUS.register("air_compressor", () -> from(AirCompressorMenu::new));

    public static final RegistryObject<MenuType<BrewingBarrelMenu>> BREWING_BARREL =
            MENUS.register("brewing_barrel", () -> from(BrewingBarrelMenu::new));

    public static final RegistryObject<MenuType<ShakerMenu>> SHAKER =
            MENUS.register("shaker", () -> new MenuType<>(ShakerMenu::new));

    private interface KKBeMenuCreator<M extends AbstractContainerMenu, T extends BlockEntity> {
        M create(int windowId, Inventory inv, T blockEntity);
    }

    private static <M extends AbstractContainerMenu, T extends BlockEntity> MenuType<M> from(KKBeMenuCreator<M, T> creator) {
        return IForgeMenuType.create((id, inv, data) ->
                creator.create(id, inv, (T) inv.player.getLevel().getBlockEntity(data.readBlockPos())));
    }
}
