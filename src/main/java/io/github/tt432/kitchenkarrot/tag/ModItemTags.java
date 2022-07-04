package io.github.tt432.kitchenkarrot.tag;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

/**
 * @author DustW
 **/
public class ModItemTags {
    public static final TagKey<Item> CONTAINER_ITEM =
            ItemTags.create(new ResourceLocation("kitchenkarrot:container_item"));

    public static final TagKey<Item> KNIFE_ITEM =
            ItemTags.create(new ResourceLocation("kitchenkarrot:knife_item"));

    public static final TagKey<Item> BASE =
            ItemTags.create(new ResourceLocation("kitchenkarrot:base"));
}
