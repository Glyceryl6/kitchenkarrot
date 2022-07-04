package io.github.tt432.kitchenkarrot.gui.base;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;

/**
 * @author DustW
 **/
public abstract class KKGui<T extends AbstractContainerMenu> extends AbstractContainerScreen<T> {
    private final ResourceLocation GUI;

    public KKGui(T pMenu, Inventory pPlayerInventory, Component pTitle, ResourceLocation gui) {
        super(pMenu, pPlayerInventory, pTitle);
        this.GUI = gui;
    }

    @Override
    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(pPoseStack);
        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
        this.renderTooltip(pPoseStack, pMouseX, pMouseY);
    }

    @Override
    protected void renderBg(PoseStack matrixStack, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.setShaderTexture(0, GUI);
        this.blit(matrixStack, leftPos, topPos, 0, 0, this.imageWidth, this.imageHeight);
    }

    protected <W extends AbstractWidget> W close(W widget) {
        widget.active = false;
        widget.visible = false;
        return widget;
    }

    protected <W extends AbstractWidget> W open(W widget) {
        widget.active = true;
        widget.visible = true;
        return widget;
    }
}
