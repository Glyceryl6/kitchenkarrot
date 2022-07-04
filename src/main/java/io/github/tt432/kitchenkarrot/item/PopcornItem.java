package io.github.tt432.kitchenkarrot.item;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

/**
 * @author DustW
 **/
public class PopcornItem extends Item {
    public PopcornItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity) {
        if (pLivingEntity instanceof Player player) {
            var prop = getFoodProperties();

            if (prop != null) {
                player.getFoodData().eat(prop.getNutrition(), prop.getSaturationModifier());
            }

            if (!player.getAbilities().instabuild) {
                if (addCount(pStack)) {
                    pStack.shrink(1);
                }
            }
        }

        return pStack;
    }

    public static boolean addCount(ItemStack itemStack) {
        var key = "popcorn_count";
        var oldValue = itemStack.getOrCreateTag().getInt(key);
        itemStack.getOrCreateTag().putInt(key, oldValue + 1);
        return oldValue + 1 == 8;
    }
}
