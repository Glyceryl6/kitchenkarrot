package io.github.tt432.kitchenkarrot.item;

import io.github.tt432.kitchenkarrot.block.ModBlocks;
import io.github.tt432.kitchenkarrot.item.food.FoodUtil;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static io.github.tt432.kitchenkarrot.Kitchenkarrot.MOD_ID;
import static io.github.tt432.kitchenkarrot.item.ModItems.defaultProperties;

public class ModBlockItems {
    public static final DeferredRegister<Item> BLOCK_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);

    //BlockItem
    public static final RegistryObject<BlockItem> AIR_COMPRESSOR = BLOCK_ITEMS.register("air_compressor", () -> new BlockItem(ModBlocks.AIR_COMPRESSOR.get(), defaultProperties()));
    public static final RegistryObject<BlockItem> BREWING_BARREL = BLOCK_ITEMS.register("brewing_barrel", () -> new BlockItem(ModBlocks.BREWING_BARREL.get(), defaultProperties()));
    public static final RegistryObject<BlockItem> COASTER = BLOCK_ITEMS.register("coaster", () -> new BlockItem(ModBlocks.COASTER.get(), defaultProperties()));
    public static final RegistryObject<BlockItem> SUNFLOWER_OIL = BLOCK_ITEMS.register("sunflower_oil", () -> new BlockItem(ModBlocks.SUNFLOWER_OIL.get(), defaultProperties()));
    public static final RegistryObject<BlockItem> CHORUS_OIL = BLOCK_ITEMS.register("chorus_oil", () -> new BlockItem(ModBlocks.CHORUS_OIL.get(), defaultProperties()));
    public static final RegistryObject<BlockItem> ACORN_OIL = BLOCK_ITEMS.register("acorn_oil", () -> new BlockItem(ModBlocks.ACORN_OIL.get(), defaultProperties()));
    public static final RegistryObject<BlockItem> ROCK_SALT = BLOCK_ITEMS.register("rock_salt", () -> new BlockItem(ModBlocks.ROCK_SALT.get(), defaultProperties()));
    public static final RegistryObject<BlockItem> FINE_SALT = BLOCK_ITEMS.register("fine_salt", () -> new BlockItem(ModBlocks.FINE_SALT.get(), defaultProperties()));
    public static final RegistryObject<BlockItem> SEA_SALT = BLOCK_ITEMS.register("sea_salt", () -> new BlockItem(ModBlocks.SEA_SALT.get(), defaultProperties()));
    public static final RegistryObject<BlockItem> GEM_CARROT = BLOCK_ITEMS.register("gem_carrot", () -> new BlockItem(ModBlocks.GEM_CARROT.get(),FoodUtil.food(defaultProperties(), 6, 8)));
}
