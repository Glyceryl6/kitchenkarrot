package io.github.tt432.kitchenkarrot.blockentity;

import io.github.tt432.kitchenkarrot.blockentity.sync.ItemStackHandlerSyncData;
import io.github.tt432.kitchenkarrot.blockentity.sync.SyncDataManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

/**
 * @author DustW
 **/
public class PlateBlockEntity extends BaseBlockEntity {
    ItemStackHandlerSyncData item;

    public PlateBlockEntity(BlockPos pWorldPosition, BlockState pBlockState) {
        super(ModBlockEntities.PLATE.get(), pWorldPosition, pBlockState);
    }

    @Override
    protected void syncDataInit(SyncDataManager manager) {
        item = manager.add(new ItemStackHandlerSyncData(this, "item", 1, true));
    }

    @Override
    public List<ItemStack> drops() {
        return Collections.singletonList(item.get().getStackInSlot(0));
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.orEmpty(cap, LazyOptional.of(item).cast());
    }
}
