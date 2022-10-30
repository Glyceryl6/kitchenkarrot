package io.github.tt432.kitchenkarrot.datagen;

import io.github.tt432.kitchenkarrot.block.PlateBlock;
import io.github.tt432.kitchenkarrot.item.ModBlockItems;
import io.github.tt432.kitchenkarrot.item.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

/**
 * @author DustW
 **/
public class TutItemModels extends ItemModelProvider {

    public TutItemModels(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, DataGenerators.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        ForgeRegistries.ITEMS.forEach(item -> {
            if (item.getRegistryName().getNamespace().equals(DataGenerators.MOD_ID)) {
                if (item instanceof BlockItem) {
                    if (item == ModItems.FOOD_FILLED_PLATE.get() || item == ModBlockItems.GEM_CARROT.get())
                        return;
                    withExistingParent(item.getRegistryName().getPath(), modLoc("block/" + item.getRegistryName().getPath()));
                } else {
                    simpleTexture(() -> item);
                }
            }
        });
    }

    void simpleTexture(Supplier<Item> itemSupplier) {
        String name = itemSupplier.get().getRegistryName().getPath();

        try {
            ResourceLocation texture = modLoc("item/" + name);

            if (existingFileHelper.exists(texture, ModelProvider.TEXTURE)) {
                singleTexture(name, mcLoc("item/generated"), "layer0", texture);
            }
        } catch (Exception ignored) {
        }
    }
}