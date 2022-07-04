package io.github.tt432.kitchenkarrot.client;

import io.github.tt432.kitchenkarrot.block.ModBlocks;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

/**
 * @author DustW
 **/
@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BlockRenderTypeRegistry {
    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.ACORN_OIL.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.CHORUS_OIL.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.SUNFLOWER_OIL.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.FINE_SALT.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.SEA_SALT.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.ROCK_SALT.get(), RenderType.cutout());
        });
    }
}
