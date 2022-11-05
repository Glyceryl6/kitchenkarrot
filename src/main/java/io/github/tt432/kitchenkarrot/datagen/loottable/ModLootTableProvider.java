package io.github.tt432.kitchenkarrot.datagen.loottable;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import io.github.tt432.kitchenkarrot.Kitchenkarrot;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ModLootTableProvider extends LootTableProvider {
    public ModLootTableProvider(DataGenerator pGenerator) {
        super(pGenerator);
    }

    @Override
    @NotNull
    public String getName() {
        return super.getName() + ":" + Kitchenkarrot.MOD_ID;
    }

    @Override
    @NotNull
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> getTables() {
        ImmutableList.Builder<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> builder = new ImmutableList.Builder<>();
        builder.add(Pair.of(ModBlockLootProvider::new, LootContextParamSets.BLOCK));
        return builder.build();
    }

    @Override
    protected void validate(@NotNull Map<ResourceLocation, LootTable> map, @NotNull ValidationContext validationtracker) {

    }
}
