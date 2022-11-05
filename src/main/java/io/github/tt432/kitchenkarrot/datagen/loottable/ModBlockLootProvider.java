package io.github.tt432.kitchenkarrot.datagen.loottable;

import io.github.tt432.kitchenkarrot.block.ModBlocks;
import io.github.tt432.kitchenkarrot.item.ModBlockItems;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ModBlockLootProvider extends BlockLoot {

    private final Set<Block> skipBlocks = new HashSet<>();

    @Override
    protected void add(@NotNull Block pBlock, @NotNull LootTable.Builder pLootTableBuilder) {
        super.add(pBlock, pLootTableBuilder);
        skipBlocks.add(pBlock);
    }

    @Override
    protected void addTables() {
        add(ModBlocks.GEM_CARROT.get(),
                createCropDrops(ModBlocks.GEM_CARROT.get(),
                        ModBlockItems.GEM_CARROT.get(),
                        ModBlockItems.GEM_CARROT.get(),
                        LootItemBlockStatePropertyCondition
                                .hasBlockStateProperties(ModBlocks.GEM_CARROT.get())
                                .setProperties(StatePropertiesPredicate.Builder.properties()
                                        .hasProperty(CropBlock.AGE, 7))));
    }

    @Override
    @NotNull
    protected Iterable<Block> getKnownBlocks() {
        return skipBlocks;
    }

}
