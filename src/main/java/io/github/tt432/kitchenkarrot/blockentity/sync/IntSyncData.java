package io.github.tt432.kitchenkarrot.blockentity.sync;

import net.minecraft.nbt.CompoundTag;

/**
 * @author DustW
 **/
public class IntSyncData extends SyncData<Integer> {
    protected static final String VALUE_KEY = "value";

    public IntSyncData(String name, Integer defaultValue, boolean needSave) {
        super(name, defaultValue, needSave);
    }

    @Override
    protected CompoundTag toTag() {
        var result = new CompoundTag();
        result.putInt(VALUE_KEY, get());
        return result;
    }

    @Override
    protected Integer fromTag(CompoundTag tag) {
        return tag.getInt(VALUE_KEY);
    }

    public int reduce(int value) {
        this.set(get() - value);
        return get();
    }

    public int reduce(int value, int min) {
        this.set(Math.max(get() - value, min));
        return get();
    }

    public int plus(int value) {
        this.set(get() + value);
        return get();
    }

    public int plus(int value, int max) {
        this.set(Math.min(get() + value, max));
        return get();
    }
}
