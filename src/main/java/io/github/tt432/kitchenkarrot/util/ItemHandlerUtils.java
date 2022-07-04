package io.github.tt432.kitchenkarrot.util;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * @author DustW
 **/
public class ItemHandlerUtils {
    public static List<ItemStack> toList(IItemHandler... itemHandlers) {
        List<ItemStack> result = new ArrayList<>();

        for (IItemHandler itemHandler : itemHandlers) {
            for (int i = 0; i < itemHandler.getSlots(); i++) {
                result.add(itemHandler.getStackInSlot(i));
            }
        }

        return result;
    }
}
