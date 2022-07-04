package io.github.tt432.kitchenkarrot.gui.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author DustW
 **/
public class ImageButtonWidget extends TooltipWidget {
    Consumer<ImageButtonWidget> onClick;
    ResourceLocation texture;
    int texX;
    int texY;

    public ImageButtonWidget(AbstractContainerScreen<?> screen,
                             int pX, int pY,
                             int pWidth, int pHeight,
                             Supplier<Component> message, boolean needTooltip,
                             Consumer<ImageButtonWidget> onClick,
                             ResourceLocation texture, int texX, int texY) {
        super(screen, pX, pY, pWidth, pHeight, message, needTooltip);

        this.onClick = onClick;
        this.texture = texture;
        this.texX = texX;
        this.texY = texY;
    }

    public ImageButtonWidget(AbstractContainerScreen<?> screen,
                             int pX, int pY,
                             int pWidth, int pHeight,
                             Consumer<ImageButtonWidget> onClick,
                             ResourceLocation texture, int texX, int texY) {
        this(screen, pX, pY, pWidth, pHeight, () -> new TextComponent(""),
                false, onClick, texture, texX, texY);
    }

    @Override
    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        if (!visible) {
            return;
        }

        RenderSystem.setShaderTexture(0, texture);
        screen.blit(pPoseStack, x, y, texX, texY, width, height);

        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
    }

    public void onPress() {
        onClick.accept(this);
    }

    @Override
    public void onClick(double pMouseX, double pMouseY) {
        this.onPress();
    }

    @Override
    public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
        if (this.active && this.visible) {
            if (pKeyCode != 257 && pKeyCode != 32 && pKeyCode != 335) {
                return false;
            } else {
                this.playDownSound(Minecraft.getInstance().getSoundManager());
                this.onPress();
                return true;
            }
        } else {
            return false;
        }
    }
}
