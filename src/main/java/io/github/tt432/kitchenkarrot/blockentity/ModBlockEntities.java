package io.github.tt432.kitchenkarrot.blockentity;

import io.github.tt432.kitchenkarrot.Kitchenkarrot;
import io.github.tt432.kitchenkarrot.block.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * @author DustW
 **/
public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, Kitchenkarrot.MOD_ID);

    public static final RegistryObject<BlockEntityType<AirCompressorBlockEntity>> AIR_COMPRESSOR =
            BLOCK_ENTITIES.register("air_compressor", () -> BlockEntityType.Builder
                    .of(AirCompressorBlockEntity::new, ModBlocks.AIR_COMPRESSOR.get()).build(null));

    public static final RegistryObject<BlockEntityType<BrewingBarrelBlockEntity>> BREWING_BARREL =
            BLOCK_ENTITIES.register("brewing_barrel", () -> BlockEntityType.Builder
                    .of(BrewingBarrelBlockEntity::new, ModBlocks.BREWING_BARREL.get()).build(null));

    public static final RegistryObject<BlockEntityType<CoasterBlockEntity>> COASTER =
            BLOCK_ENTITIES.register("coaster", () -> BlockEntityType.Builder
                    .of(CoasterBlockEntity::new, ModBlocks.COASTER.get()).build(null));

    public static final RegistryObject<BlockEntityType<PlateBlockEntity>> PLATE =
            BLOCK_ENTITIES.register("plate", () -> BlockEntityType.Builder
                    .of(PlateBlockEntity::new, ModBlocks.PLATE.get()).build(null));
}
