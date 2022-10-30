package io.github.tt432.kitchenkarrot.item.food;

import io.github.tt432.kitchenkarrot.item.EffectEntry;
import io.github.tt432.kitchenkarrot.item.ModItems;
import net.minecraft.world.item.Item;

public class FoodItem extends Item {

    public FoodItem(int nutrition, float saturation, EffectEntry... effectEntries) {
        super(FoodUtil.effectFood(ModItems.defaultProperties(), nutrition, saturation, effectEntries));
    }
}
