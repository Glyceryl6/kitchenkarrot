package io.github.tt432.kitchenkarrot.blockentity.sync;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.NonNullSupplier;
import org.jetbrains.annotations.NotNull;

/**
 * @author DustW
 **/
public abstract class SyncData<V> implements NonNullSupplier<V> {
    private V value;
    private boolean changed;
    private final String name;
    public final boolean needSave;

    public SyncData(String name, V defaultValue, boolean needSave) {
        this.value = defaultValue;
        this.name = name;
        this.needSave = needSave;
    }

    protected abstract CompoundTag toTag();
    protected abstract V fromTag(CompoundTag tag);

    @Override
    public @NotNull V get() {
        return value;
    }

    public void set(V value) {
        this.value = value;
        onChanged();
    }

    public void save(CompoundTag tag, boolean force) {
        if (changed || force) {
            tag.put(name, toTag());
            changed = false;
        }
    }

    public void load(CompoundTag tag) {
        if (tag.contains(name)) {
            value = fromTag(tag.getCompound(name));
        }
    }

    protected void onChanged() {
        this.changed = true;
    }
}
