package io.github.tt432.kitchenkarrot.item.food;

import io.github.tt432.kitchenkarrot.item.ModItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;

public class CurryUdonItem extends Item {
    public CurryUdonItem(int nutrition, float saturation) {
        super(FoodUtil.food(ModItems.defaultProperties(), nutrition, saturation).stacksTo(1).rarity(Rarity.RARE));
    }

    @Override
    public int getUseDuration(ItemStack itemStack) {
        return 48;
    }
}
