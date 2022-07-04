package io.github.tt432.kitchenkarrot.client.renderer.be;

import io.github.tt432.kitchenkarrot.blockentity.ModBlockEntities;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * @author DustW
 **/
@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BlockEntityRegistry {
    @SubscribeEvent
    public static void onEvent(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ModBlockEntities.COASTER.get(), (c) -> new CoasterBlockEntityRenderer());
        event.registerBlockEntityRenderer(ModBlockEntities.PLATE.get(), (c) -> new PlateBlockEntityRenderer());
    }
}
