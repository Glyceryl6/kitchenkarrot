package io.github.tt432.kitchenkarrot.block;

import io.github.tt432.kitchenkarrot.blockentity.AirCompressorBlockEntity;
import io.github.tt432.kitchenkarrot.blockentity.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class AirCompressorBlock extends FacingGuiEntityBlock<AirCompressorBlockEntity>{
    public static final VoxelShape SHAPE = Block.box(2, 0, 2, 16 - 2, 15, 16 - 2);

    protected AirCompressorBlock() {
        super(BlockBehaviour.Properties.of(Material.STONE)
                .strength(2.0f, 2.0f)
                .noOcclusion());
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public BlockEntityType<AirCompressorBlockEntity> getBlockEntity() {
        return ModBlockEntities.AIR_COMPRESSOR.get();
    }
}
