package io.github.tt432.kitchenkarrot.item;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

/**
 * @author DustW
 **/
public class EatFastItem extends Item implements RemainItemItem {
    int tick;
    ItemStack remainItem = ItemStack.EMPTY;

    public EatFastItem(Properties properties, int tick) {
        super(properties);
        this.tick = tick;
    }

    public EatFastItem(Properties p_42979_, int tick, ItemStack remainItem) {
        this(p_42979_, tick);
        this.remainItem = remainItem;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack itemStack, Level level, LivingEntity livingEntity) {
        itemStack = livingEntity.eat(level, itemStack);

        if (itemStack.isEmpty()) {
            return getRemainItem();
        }

        if (livingEntity instanceof Player player) {
            player.getInventory().add(getRemainItem());
        }

        return itemStack;
    }

    @Override
    public int getUseDuration(ItemStack p_43001_) {
        return tick;
    }

    @Override
    public ItemStack getRemainItem() {
        return remainItem.copy();
    }
}
