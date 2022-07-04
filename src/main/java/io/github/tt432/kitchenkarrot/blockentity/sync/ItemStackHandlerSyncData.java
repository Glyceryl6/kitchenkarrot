package io.github.tt432.kitchenkarrot.blockentity.sync;

import io.github.tt432.kitchenkarrot.capability.KKItemStackHandler;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;

/**
 * @author DustW
 **/
public class ItemStackHandlerSyncData extends SyncData<ItemStackHandlerSyncData.SyncDataItemStackHandler> {
    public ItemStackHandlerSyncData(BlockEntity blockEntity, String name, int size, boolean needSave) {
        super(name, new SyncDataItemStackHandler(blockEntity, size), needSave);
        get().setData(this);
    }

    @Override
    public void set(SyncDataItemStackHandler value) {
        super.set(value);
    }

    @Override
    protected CompoundTag toTag() {
        return get().serializeNBT();
    }

    @Override
    protected SyncDataItemStackHandler fromTag(CompoundTag tag) {
        get().deserializeNBT(tag);
        return get();
    }

    public static class SyncDataItemStackHandler extends KKItemStackHandler {

        ItemStackHandlerSyncData data;

        public SyncDataItemStackHandler(BlockEntity be, int size) {
            super(be, size);
        }

        public void setData(ItemStackHandlerSyncData data) {
            this.data = data;
        }

        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            data.onChanged();
        }
    }
}
