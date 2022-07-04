package io.github.tt432.kitchenkarrot.gui.widget;

import com.mojang.blaze3d.vertex.PoseStack;
import io.github.tt432.kitchenkarrot.client.renderer.FluidStackRenderer;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraftforge.fluids.IFluidTank;

import java.util.function.Supplier;

/**
 * @author DustW
 **/
public class FluidSlotWidget extends TooltipWidget {

    IFluidTank tank;
    FluidStackRenderer renderer;

    public FluidSlotWidget(AbstractContainerScreen<?> screen,
                           int pX, int pY,
                           int pWidth, int pHeight,
                           Supplier<Component> message, boolean needTooltip,
                           IFluidTank tank) {
        super(screen, pX, pY, pWidth, pHeight, message, needTooltip);

        this.tank = tank;
        this.renderer = new FluidStackRenderer(tank.getCapacity(), width, height);
    }

    @Override
    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        renderer.render(pPoseStack, x, y, tank.getFluid());

        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
    }

    @Override
    protected boolean clicked(double pMouseX, double pMouseY) {
        return false;
    }
}
