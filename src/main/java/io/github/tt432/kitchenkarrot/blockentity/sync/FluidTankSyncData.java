package io.github.tt432.kitchenkarrot.blockentity.sync;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import java.util.function.Predicate;

/**
 * @author DustW
 **/
public class FluidTankSyncData extends SyncData<FluidTankSyncData.SyncDataFluidTank> {
    public FluidTankSyncData(String name, int capacity, Predicate<FluidStack> validator, boolean needSave) {
        super(name, new SyncDataFluidTank(capacity, validator), needSave);
        get().setSyncData(this);
    }

    public FluidTankSyncData(String name, int capacity, boolean needSave) {
        this(name, capacity, (f) -> true, needSave);
    }

    @Override
    public void set(SyncDataFluidTank value) {
    }

    @Override
    protected CompoundTag toTag() {
        return get().writeToNBT(new CompoundTag());
    }

    @Override
    protected SyncDataFluidTank fromTag(CompoundTag tag) {
        return get().readFromNBT(tag);
    }

    public static class SyncDataFluidTank extends FluidTank {
        FluidTankSyncData syncData;

        public SyncDataFluidTank(int capacity, Predicate<FluidStack> validator) {
            super(capacity, validator);
        }

        protected void setSyncData(FluidTankSyncData syncData) {
            this.syncData = syncData;
        }

        @Override
        protected void onContentsChanged() {
            if (syncData != null) {
                syncData.onChanged();
            }
        }

        @Override
        public SyncDataFluidTank readFromNBT(CompoundTag tag) {
            super.readFromNBT(tag);
            return this;
        }
    }
}
