package io.github.tt432.kitchenkarrot.client;

import io.github.tt432.kitchenkarrot.client.renderer.entity.CanEntityRender;
import io.github.tt432.kitchenkarrot.entity.ModEntitys;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class EntityRenderRegistry {
    @SubscribeEvent
    public static void onEntityRenderRegistryEvent(FMLClientSetupEvent event) {
        EntityRenderers.register(ModEntitys.CAN.get(), CanEntityRender::new);
    }
}
