package io.github.tt432.kitchenkarrot.item;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

/**
 * @author DustW
 **/
public class ClearEffectItem extends Item {
    public ClearEffectItem(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack p_41409_, Level p_41410_, LivingEntity p_41411_) {
        p_41411_.removeAllEffects();
        return super.finishUsingItem(p_41409_, p_41410_, p_41411_);
    }
}
