package io.github.tt432.kitchenkarrot.client.plate;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * @author DustW
 **/
public class PlateBakedModel implements BakedModel {
    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState p_119123_, @Nullable Direction p_119124_, Random p_119125_) {
        return Collections.emptyList();
    }

    @Override
    public boolean useAmbientOcclusion() {
        return false;
    }

    @Override
    public boolean isGui3d() {
        return false;
    }

    @Override
    public boolean usesBlockLight() {
        return true;
    }

    @Override
    public boolean isCustomRenderer() {
        return false;
    }

    @Override
    public TextureAtlasSprite getParticleIcon() {
        return null;
    }

    static Map<ModelResourceLocation, BakedModel> cache = new HashMap<>();

    @Override
    public ItemOverrides getOverrides() {
        return new ItemOverrides() {
            @Nullable
            @Override
            public BakedModel resolve(BakedModel model, ItemStack stack, @Nullable ClientLevel p_173467_, @Nullable LivingEntity p_173468_, int p_173469_) {
                var tag = stack.getTag();

                if (tag != null && tag.contains("plate_type") && tag.contains("plate_amount")) {
                    var location = new ResourceLocation(
                            tag.getString("plate_type") + "_" + tag.getInt("plate_amount")
                    );

                    return PlateModelRegistry.get(location);
                }

                return PlateModelRegistry.DEFAULT_MODEL;
            }
        };
    }
}
