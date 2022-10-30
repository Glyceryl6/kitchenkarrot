package io.github.tt432.kitchenkarrot.datagen.loottable;

import io.github.tt432.kitchenkarrot.block.ModBlocks;
import io.github.tt432.kitchenkarrot.item.ModBlockItems;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;

public class ModBlockLootProvider extends BlockLoot {

    @Override
    protected void addTables() {
        createCropDrops(ModBlocks.GEM_CARROT.get(), ModBlockItems.GEM_CARROT.get(), ModBlockItems.GEM_CARROT.get(),
                LootItemBlockStatePropertyCondition
                        .hasBlockStateProperties(Blocks.WHEAT)
                        .setProperties(StatePropertiesPredicate.Builder.properties()
                                .hasProperty(CropBlock.AGE, 7)));
    }
}
