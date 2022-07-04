package io.github.tt432.kitchenkarrot.datagen;

import io.github.tt432.kitchenkarrot.item.ModItems;
import io.github.tt432.kitchenkarrot.tag.ModItemTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.data.ExistingFileHelper;

/**
 * @author DustW
 **/
public class TutItemTags extends ItemTagsProvider {

    public TutItemTags(DataGenerator generator, BlockTagsProvider blockTags, ExistingFileHelper helper) {
        super(generator, blockTags, DataGenerators.MOD_ID, helper);
    }

    @Override
    protected void addTags() {
        tag(ModItemTags.CONTAINER_ITEM)
                .add(Items.GLASS_BOTTLE)
                .add(ModItems.EMPTY_CAN.get());
    }

    @Override
    public String getName() {
        return "Tutorial Tags";
    }
}
