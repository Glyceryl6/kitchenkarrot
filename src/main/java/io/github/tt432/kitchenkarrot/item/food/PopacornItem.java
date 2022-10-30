package io.github.tt432.kitchenkarrot.item.food;

import io.github.tt432.kitchenkarrot.item.ModItems;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class PopacornItem extends Item {
    public PopacornItem() {
        super(FoodUtil.food(ModItems.defaultProperties(), 2, 3.2F).defaultDurability(8));
    }

//    @Override
//    @NotNull
//    public ItemStack finishUsingItem(@NotNull ItemStack itemStack, @NotNull Level level, @NotNull LivingEntity livingEntity) {
//        if (livingEntity instanceof Player) {
//            itemStack.setDamageValue(itemStack.getDamageValue() - 1);
//            if (itemStack.isDamaged()) {
//                itemStack.shrink(1);
//            }
//        }
//        return itemStack;
//    }


    @Override
    public int getUseDuration(ItemStack pStack) {
        return 24;
    }
}
