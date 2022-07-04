package io.github.tt432.kitchenkarrot.datagen;

import io.github.tt432.kitchenkarrot.Kitchenkarrot;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

/**
 * @author DustW
 **/
@Mod.EventBusSubscriber(modid = DataGenerators.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    public static final String MOD_ID = Kitchenkarrot.MOD_ID;

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        var file = event.getExistingFileHelper();

        if (event.includeClient()) {
            generator.addProvider(new TutItemModels(generator, event.getExistingFileHelper()));
        }

        if (event.includeServer()) {
            var blockTag = new TutBlockTags(generator, file);
            generator.addProvider(blockTag);
            generator.addProvider(new TutItemTags(generator, blockTag, event.getExistingFileHelper()));
        }
    }
}