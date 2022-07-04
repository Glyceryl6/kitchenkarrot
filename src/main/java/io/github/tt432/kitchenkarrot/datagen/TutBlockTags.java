package io.github.tt432.kitchenkarrot.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

/**
 * @author DustW
 **/
public class TutBlockTags extends BlockTagsProvider {

    public TutBlockTags(DataGenerator generator, ExistingFileHelper helper) {
        super(generator, DataGenerators.MOD_ID, helper);
    }

    @Override
    protected void addTags() {
    }

    @Override
    public String getName() {
        return DataGenerators.MOD_ID + " Tags";
    }
}