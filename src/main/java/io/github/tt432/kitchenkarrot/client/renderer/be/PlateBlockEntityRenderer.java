package io.github.tt432.kitchenkarrot.client.renderer.be;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import io.github.tt432.kitchenkarrot.block.CoasterBlock;
import io.github.tt432.kitchenkarrot.blockentity.PlateBlockEntity;
import io.github.tt432.kitchenkarrot.client.ModModelRegistry;
import io.github.tt432.kitchenkarrot.client.plate.PlateModelRegistry;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.items.CapabilityItemHandler;

/**
 * @author DustW
 **/
public class PlateBlockEntityRenderer implements BlockEntityRenderer<PlateBlockEntity> {
    @Override
    public void render(PlateBlockEntity pBlockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        pBlockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(handler -> {
            var stack = handler.getStackInSlot(0);
            var model = PlateModelRegistry.get(
                    new ResourceLocation(stack.getItem().getRegistryName() + "_" + stack.getCount()));

            poseStack.pushPose();

            var state = pBlockEntity.getBlockState();

            poseStack.translate(.5, .5, .5);

            poseStack.mulPose(Vector3f.YP.rotationDegrees(
                    switch (state.getValue(CoasterBlock.FACING)) {
                        case EAST -> 90;
                        case WEST -> -90;
                        case SOUTH -> 180;
                        default -> 0;
                    }
            ));

            poseStack.translate(-.5, -.5, -.5);

            ModModelRegistry.render(model, bufferSource, pBlockEntity, poseStack, packedLight, packedOverlay);

            poseStack.popPose();
        });
    }
}
