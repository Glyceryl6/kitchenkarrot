package io.github.tt432.kitchenkarrot.datagen;

import io.github.tt432.kitchenkarrot.Kitchenkarrot;
import io.github.tt432.kitchenkarrot.datagen.loottable.ModLootTableProvider;
import io.github.tt432.kitchenkarrot.datagen.loottable.ModGlobalLootModifierProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
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
        ExistingFileHelper file = event.getExistingFileHelper();

        if (event.includeClient()) {
            generator.addProvider(new TutItemModels(generator, file));
        }

        if (event.includeServer()) {
            event.getGenerator().addProvider(new ModLootTableProvider(event.getGenerator()));
            var blockTag = new TutBlockTags(generator, file);
            generator.addProvider(blockTag);
            generator.addProvider(new TutItemTags(generator, blockTag, file));
            generator.addProvider(new ModGlobalLootModifierProvider(generator, Kitchenkarrot.MOD_ID));
        }
    }
}