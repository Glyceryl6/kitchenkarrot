package io.github.tt432.kitchenkarrot.block;

import io.github.tt432.kitchenkarrot.Kitchenkarrot;
import io.github.tt432.kitchenkarrot.blockentity.AirCompressorBlockEntity;
import io.github.tt432.kitchenkarrot.blockentity.BrewingBarrelBlockEntity;
import io.github.tt432.kitchenkarrot.blockentity.ModBlockEntities;
import io.github.tt432.kitchenkarrot.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author DustW
 **/
public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, Kitchenkarrot.MOD_ID);

    public static final RegistryObject<Block> AIR_COMPRESSOR = BLOCKS.register("air_compressor",
            () -> new FacingGuiEntityBlock<AirCompressorBlockEntity>(BlockBehaviour.Properties.of(Material.STONE)
                    .strength(2.0f, 2.0f)
                    .noOcclusion()) {
                @Override
                public BlockEntityType<AirCompressorBlockEntity> getBlockEntity() {
                    return ModBlockEntities.AIR_COMPRESSOR.get();
                }
            });

    public static final RegistryObject<Block> BREWING_BARREL = BLOCKS.register("brewing_barrel",
            () -> new FacingGuiEntityBlock<BrewingBarrelBlockEntity>(BlockBehaviour.Properties.of(Material.WOOD)
                    .strength(2.0f, 2.0f)
                    .noOcclusion()) {

                @Override
                public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
                    AtomicBoolean changed = new AtomicBoolean(false);

                    pLevel.getBlockEntity(pPos).getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY).ifPresent(handler -> {
                        if (handler instanceof IFluidTank tank) {
                            var item = pPlayer.getItemInHand(pHand);
                            var remain = ItemStack.EMPTY;

                            if (item.getItem() == Items.WATER_BUCKET) {
                                var water = new FluidStack(Fluids.WATER, 1000);

                                if (tank.fill(water, IFluidHandler.FluidAction.SIMULATE) == 1000) {
                                    changed.set(true);
                                    remain = new ItemStack(Items.BUCKET);

                                    if (!pLevel.isClientSide) {
                                        tank.fill(water, IFluidHandler.FluidAction.EXECUTE);
                                    }
                                }
                            } else if (item.getItem() == Items.POTION && PotionUtils.getAllEffects(item.getTag()).isEmpty()) {
                                var water = new FluidStack(Fluids.WATER, 250);

                                if (tank.fill(water, IFluidHandler.FluidAction.SIMULATE) == 250) {
                                    changed.set(true);
                                    remain = new ItemStack(Items.GLASS_BOTTLE);

                                    if (!pLevel.isClientSide) {
                                        tank.fill(water, IFluidHandler.FluidAction.EXECUTE);
                                    }
                                }
                            }
                            else if (item.getItem() == ModItems.WATER.get()) {
                                var water = new FluidStack(Fluids.WATER, 125);

                                if (tank.fill(water, IFluidHandler.FluidAction.SIMULATE) == 125) {
                                    changed.set(true);

                                    if (!pLevel.isClientSide) {
                                        tank.fill(water, IFluidHandler.FluidAction.EXECUTE);
                                    }
                                }
                            }

                            if (changed.get()) {
                                if (!pPlayer.getAbilities().instabuild) {
                                    item.shrink(1);
                                    pPlayer.getInventory().add(remain);
                                }

                                pPlayer.playSound(SoundEvents.BUCKET_EMPTY, 0.5F,
                                        pLevel.random.nextFloat() * 0.1F + 0.9F);
                            }
                        }
                    });

                    if (changed.get()) {
                        return InteractionResult.SUCCESS;
                    }

                    return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
                }

                @Override
                public BlockEntityType<BrewingBarrelBlockEntity> getBlockEntity() {
                    return ModBlockEntities.BREWING_BARREL.get();
                }
            });

    public static final RegistryObject<Block> ROCK_SALT = salt("rock_salt");
    public static final RegistryObject<Block> SEA_SALT = salt("sea_salt");
    public static final RegistryObject<Block> FINE_SALT = salt("fine_salt");

    public static final RegistryObject<Block> SUNFLOWER_OIL = oil("sunflower_oil");
    public static final RegistryObject<Block> ACORN_OIL = oil("acorn_oil");
    public static final RegistryObject<Block> CHORUS_OIL = oil("chorus_oil");

    public static final RegistryObject<Block> COASTER = BLOCKS.register("coaster",
            () -> new CoasterBlock(BlockBehaviour.Properties.of(Material.STONE)
                    .strength(2.0F, 2.0F).noCollission()));

    public static final RegistryObject<Block> PLATE = BLOCKS.register("plate",
            () -> new PlateBlock(BlockBehaviour.Properties.of(Material.STONE)
                    .strength(2.0F, 2.0F).noCollission()));

    private static RegistryObject<Block> oil(String name) {
        return BLOCKS.register(name,
                () -> new Block(BlockBehaviour.Properties.of(Material.STONE).noOcclusion()
                        .strength(2.0f, 2.0f)) {
                    @Override
                    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
                        return OIL;
                    }
                });
    }

    private static RegistryObject<Block> salt(String name) {
        return BLOCKS.register(name,
                () -> new Block(BlockBehaviour.Properties.of(Material.STONE).noOcclusion()
                        .strength(2.0f, 2.0f)) {
                    @Override
                    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
                        return SALT;
                    }
                });
    }

    protected static final VoxelShape OIL = Shapes.or(
            Block.box(
                    5, 0, 5,
                    11, 5, 11),
            Block.box(
                    6, 5, 6,
                    10, 6, 10)
    );

    protected static final VoxelShape SALT = Shapes.or(
            Block.box(
                    6, 0, 6,
                    10, 7, 10),
            Block.box(
                    7, 7, 7,
                    9, 9, 9)
    );
}
