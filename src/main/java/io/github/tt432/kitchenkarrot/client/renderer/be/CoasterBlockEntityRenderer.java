package io.github.tt432.kitchenkarrot.client.renderer.be;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import io.github.tt432.kitchenkarrot.block.CoasterBlock;
import io.github.tt432.kitchenkarrot.blockentity.CoasterBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraftforge.items.CapabilityItemHandler;

/**
 * @author DustW
 **/
public class CoasterBlockEntityRenderer implements BlockEntityRenderer<CoasterBlockEntity> {
    @Override
    public void render(CoasterBlockEntity pBlockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        pBlockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(handler -> {
            poseStack.pushPose();

            poseStack.translate(0.5, 4 / 16., 0.5);
            poseStack.scale(1.7f, 1.7f, 1.7f);

            var state = pBlockEntity.getBlockState();

            poseStack.mulPose(Vector3f.YP.rotationDegrees(
                    switch (state.getValue(CoasterBlock.FACING)) {
                        case EAST -> 90;
                        case WEST -> -90;
                        case SOUTH -> 180;
                        default -> 0;
                    }
            ));

            var itemRenderer = Minecraft.getInstance().getItemRenderer();
            var item = handler.getStackInSlot(0);

            itemRenderer.renderStatic(item, ItemTransforms.TransformType.GROUND,
                    LightTexture.FULL_BRIGHT, packedOverlay,
                    poseStack, bufferSource, item.getItem().getRegistryName().hashCode());

            poseStack.popPose();
        });
    }
}
