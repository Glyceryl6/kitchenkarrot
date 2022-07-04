package io.github.tt432.kitchenkarrot.gui.widget;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;

import java.util.function.Supplier;

/**
 * @author DustW
 **/
public class TooltipWidget extends AbstractWidget {
    Supplier<Component> message;
    boolean needTooltip;
    AbstractContainerScreen<?> screen;

    public TooltipWidget(AbstractContainerScreen<?> screen,
                         int pX, int pY,
                         int pWidth, int pHeight,
                         Supplier<Component> message, boolean needTooltip) {
        super(pX, pY, pWidth, pHeight, message.get());

        this.screen = screen;
        this.message = message;
        this.needTooltip = needTooltip;
    }

    @Override
    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        if (!visible) {
            return;
        }

        this.isHovered = pMouseX >= this.x && pMouseY >= this.y && pMouseX < this.x + this.width && pMouseY < this.y + this.height;

        renderToolTip(pPoseStack, pMouseX, pMouseY);
    }

    @Override
    public void renderToolTip(PoseStack pPoseStack, int pMouseX, int pMouseY) {
        if (isHovered && needTooltip) {
            screen.renderTooltip(pPoseStack, getMessage(), pMouseX, pMouseY);
        }
    }

    @Override
    public Component getMessage() {
        return message.get();
    }

    @Override
    public void updateNarration(NarrationElementOutput pNarrationElementOutput) {
        if (this.needTooltip) {
            pNarrationElementOutput.add(NarratedElementType.HINT, getMessage());
        }
    }
}
