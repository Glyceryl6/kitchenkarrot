package io.github.tt432.kitchenkarrot.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import io.github.tt432.kitchenkarrot.Kitchenkarrot;
import io.github.tt432.kitchenkarrot.entity.CanEntity;
import io.github.tt432.kitchenkarrot.entity.CanEntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class CanEntityRender extends EntityRenderer<CanEntity> {
    CanEntityModel<?> canEntityModel;

    public CanEntityRender(EntityRendererProvider.Context p_174008_) {
        super(p_174008_);
        canEntityModel = new CanEntityModel<>(CanEntityModel.createBodyLayer().bakeRoot());
    }

    @Override
    public void render(CanEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
        pMatrixStack.pushPose();
        pMatrixStack.translate(0, -1.2, 0);
        this.canEntityModel.renderToBuffer(pMatrixStack, pBuffer.getBuffer(RenderType.entityCutout(getTextureLocation(pEntity))), pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        pMatrixStack.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(CanEntity pEntity) {
        return new ResourceLocation(Kitchenkarrot.MOD_ID, "textures/entity/can.png");
    }
}
