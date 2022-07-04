package io.github.tt432.kitchenkarrot.blockentity.sync;

import net.minecraft.nbt.CompoundTag;

/**
 * @author DustW
 **/
public class BoolSyncData extends SyncData<Boolean> {
    public BoolSyncData(String name, Boolean defaultValue, boolean needSave) {
        super(name, defaultValue, needSave);
    }

    @Override
    protected CompoundTag toTag() {
        var result = new CompoundTag();
        result.putBoolean("value", get());
        return result;
    }

    @Override
    protected Boolean fromTag(CompoundTag tag) {
        return tag.getBoolean("value");
    }
}
