package io.github.tt432.kitchenkarrot.item;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

/**
 * @author DustW
 **/
public class InstantHealthFood extends Item {
    public InstantHealthFood(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack p_41409_, Level p_41410_, LivingEntity p_41411_) {
        p_41411_.heal(8);
        return super.finishUsingItem(p_41409_, p_41410_, p_41411_);
    }
}
