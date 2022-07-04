package io.github.tt432.kitchenkarrot.blockentity.sync;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

/**
 * @author DustW
 **/
public class ItemSyncData extends SyncData<ItemStack> {
    public ItemSyncData(String name, ItemStack defaultValue, boolean needSave) {
        super(name, defaultValue, needSave);
    }

    @Override
    protected CompoundTag toTag() {
        return get().serializeNBT();
    }

    @Override
    protected ItemStack fromTag(CompoundTag tag) {
        return ItemStack.of(tag);
    }
}
