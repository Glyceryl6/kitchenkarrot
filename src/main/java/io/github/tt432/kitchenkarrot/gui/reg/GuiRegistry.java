package io.github.tt432.kitchenkarrot.gui.reg;

import io.github.tt432.kitchenkarrot.gui.AirCompressorGui;
import io.github.tt432.kitchenkarrot.gui.BrewingBarrelGui;
import io.github.tt432.kitchenkarrot.gui.ShakerGui;
import io.github.tt432.kitchenkarrot.menu.reg.ModMenuTypes;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

/**
 * @author DustW
 **/
@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class GuiRegistry {
    @SubscribeEvent
    public static void init(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            MenuScreens.register(ModMenuTypes.AIR_COMPRESSOR.get(), AirCompressorGui::new);
            MenuScreens.register(ModMenuTypes.SHAKER.get(), ShakerGui::new);
            MenuScreens.register(ModMenuTypes.BREWING_BARREL.get(), BrewingBarrelGui::new);
        });
    }
}
