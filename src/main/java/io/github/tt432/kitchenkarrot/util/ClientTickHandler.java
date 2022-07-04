package io.github.tt432.kitchenkarrot.util;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * @author DustW
 */
@Mod.EventBusSubscriber(Dist.CLIENT)
public final class ClientTickHandler {

    private ClientTickHandler() {}

    public static int ticksInGame = 0;

    @SubscribeEvent
    public static void clientTick(TickEvent.ClientTickEvent event) {
        if (!Minecraft.getInstance().isPaused()) {
            ticksInGame++;
        }
    }
}
