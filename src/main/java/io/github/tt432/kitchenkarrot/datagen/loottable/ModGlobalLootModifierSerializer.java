package io.github.tt432.kitchenkarrot.datagen.loottable;

import com.google.gson.JsonObject;
import io.github.tt432.kitchenkarrot.item.ModItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistry;

import java.util.Objects;

public class ModGlobalLootModifierSerializer extends GlobalLootModifierSerializer<AddLootTableModifier> {
    public AddLootTableModifier read(ResourceLocation location, JsonObject object, LootItemCondition[] conditions) {
        Item addedItem = ForgeRegistries.ITEMS.getValue(new ResourceLocation(GsonHelper.getAsString(object, "item")));
        int weight = GsonHelper.getAsInt(object, "weight", 40);
        return new AddLootTableModifier(conditions, addedItem,weight);
    }

    public JsonObject write(AddLootTableModifier instance) {
        JsonObject object = this.makeConditions(instance.conditions);
        object.addProperty("item", Objects.requireNonNull(instance.getItem().getRegistryName()).toString());
        object.addProperty("weight",instance.getWeight());
        return object;
    }

}
