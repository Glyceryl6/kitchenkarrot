package io.github.tt432.kitchenkarrot.menu;

import io.github.tt432.kitchenkarrot.blockentity.BrewingBarrelBlockEntity;
import io.github.tt432.kitchenkarrot.menu.base.KKBeMenu;
import io.github.tt432.kitchenkarrot.menu.reg.ModMenuTypes;
import net.minecraft.world.entity.player.Inventory;

/**
 * @author DustW
 **/
public class BrewingBarrelMenu extends KKBeMenu<BrewingBarrelBlockEntity> {
    public BrewingBarrelMenu(int windowId, Inventory inv, BrewingBarrelBlockEntity blockEntity) {
        super(ModMenuTypes.BREWING_BARREL.get(), windowId, inv, blockEntity);

        addSlots();
    }

    void addSlots() {
        var handler = blockEntity.input2;
        addSlot(handler, 0, 36 + 1, 20 + 1);
        addSlot(handler, 1, 54 + 1, 20 + 1);
        addSlot(handler, 2, 72 + 1, 20 + 1);
        addSlot(handler, 3, 36 + 1, 38 + 1);
        addSlot(handler, 4, 54 + 1, 38 + 1);
        addSlot(handler, 5, 72 + 1, 38 + 1);

        addResultSlot(blockEntity.result(), 0, 124 + 1, 20 + 1);
    }
}
