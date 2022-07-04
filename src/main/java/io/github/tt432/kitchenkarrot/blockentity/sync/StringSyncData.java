package io.github.tt432.kitchenkarrot.blockentity.sync;

import net.minecraft.nbt.CompoundTag;

/**
 * @author DustW
 **/
public class StringSyncData extends SyncData<String> {
    protected static final String VALUE_KEY = "value";

    public StringSyncData(String name, String defaultValue, boolean needSave) {
        super(name, defaultValue, needSave);
    }

    public boolean isEmpty() {
        return get() == null || get().isEmpty();
    }

    @Override
    protected CompoundTag toTag() {
        var result = new CompoundTag();
        result.putString(VALUE_KEY, get());
        return result;
    }

    @Override
    protected String fromTag(CompoundTag tag) {
        return tag.getString(VALUE_KEY);
    }
}
