package io.github.tt432.kitchenkarrot.item;

import io.github.tt432.kitchenkarrot.entity.CanEntity;
import io.github.tt432.kitchenkarrot.entity.ModEntitys;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class CannedItem extends EatFastItem {
    public CannedItem(Properties properties, int tick) {
        super(properties, tick);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack itemStack, Level level, LivingEntity livingEntity) {
        CanEntity canEntity = new CanEntity(ModEntitys.CAN.get(), level);
        canEntity.setPos(livingEntity.position());
        canEntity.moveRelative(1, livingEntity.getLookAngle());
        level.addFreshEntity(canEntity);
        return super.finishUsingItem(itemStack, level, livingEntity);
    }
}
