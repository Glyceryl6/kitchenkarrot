package io.github.tt432.kitchenkarrot.capability;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.ItemStackHandler;

/**
 * @author DustW
 **/
public class KKItemStackHandler extends ItemStackHandler {
    BlockEntity be;

    public KKItemStackHandler(BlockEntity be, int size) {
        super(size);
        this.be = be;
    }

    @Override
    protected void onContentsChanged(int slot) {
        be.setChanged();
    }
}
