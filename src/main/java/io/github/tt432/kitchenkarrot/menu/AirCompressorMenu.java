package io.github.tt432.kitchenkarrot.menu;

import io.github.tt432.kitchenkarrot.blockentity.AirCompressorBlockEntity;
import io.github.tt432.kitchenkarrot.menu.base.KKBeMenu;
import io.github.tt432.kitchenkarrot.menu.reg.ModMenuTypes;
import net.minecraft.world.entity.player.Inventory;

/**
 * @author DustW
 **/
public class AirCompressorMenu extends KKBeMenu<AirCompressorBlockEntity> {
    public AirCompressorMenu(int windowId, Inventory inv, AirCompressorBlockEntity blockEntity) {
        super(ModMenuTypes.AIR_COMPRESSOR.get(), windowId, inv, blockEntity);
        addItemSlots();
    }

    void addItemSlots() {
        var input1 = blockEntity.getInput1();
        addSlot(input1, 0, 32 + 1, 25 + 1);
        addSlot(input1, 1, 50 + 1, 25 + 1);
        addSlot(input1, 2, 32 + 1, 43 + 1);
        addSlot(input1, 3, 50 + 1, 43 + 1);
        addSlot(input1, 4, 81 + 1, 14 + 1);
        var input2 = blockEntity.getInput2();
        addSlot(input2, 0, 74 + 1, 58 + 1);
        var output = blockEntity.getOutput();
        addResultSlot(output, 0, 120 + 1, 34 + 1);
    }
}
