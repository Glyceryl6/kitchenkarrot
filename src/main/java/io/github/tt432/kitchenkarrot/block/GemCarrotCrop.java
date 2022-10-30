package io.github.tt432.kitchenkarrot.block;

import io.github.tt432.kitchenkarrot.item.ModBlockItems;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import org.jetbrains.annotations.NotNull;

public class GemCarrotCrop extends CropBlock {
    public GemCarrotCrop() {
        super(BlockBehaviour.Properties
                .of(Material.PLANT)
                .sound(SoundType.CROP)
                .randomTicks()
                .noCollission()
                .instabreak());
    }

    @Override
    @NotNull
    protected ItemLike getBaseSeedId() {
        return ModBlockItems.GEM_CARROT.get();
    }
}
