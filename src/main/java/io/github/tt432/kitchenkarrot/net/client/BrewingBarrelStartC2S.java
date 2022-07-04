package io.github.tt432.kitchenkarrot.net.client;

import io.github.tt432.kitchenkarrot.blockentity.BrewingBarrelBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * @author DustW
 **/
public class BrewingBarrelStartC2S {
    BlockPos pos;

    public BrewingBarrelStartC2S(BlockPos pos) {
        this.pos = pos;
    }

    public BrewingBarrelStartC2S(FriendlyByteBuf buf) {
        pos = buf.readBlockPos();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBlockPos(pos);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context ctx = supplier.get();
        ctx.enqueueWork(() -> {
            var player = ctx.getSender();
            var level = player.getLevel();

            if (level.getBlockEntity(pos) instanceof BrewingBarrelBlockEntity bb) {
                bb.start();
            }
        });
        return true;
    }
}