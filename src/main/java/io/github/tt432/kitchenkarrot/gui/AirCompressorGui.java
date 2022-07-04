package io.github.tt432.kitchenkarrot.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import io.github.tt432.kitchenkarrot.Kitchenkarrot;
import io.github.tt432.kitchenkarrot.blockentity.AirCompressorBlockEntity;
import io.github.tt432.kitchenkarrot.gui.base.KKGui;
import io.github.tt432.kitchenkarrot.gui.widget.ProgressWidget;
import io.github.tt432.kitchenkarrot.menu.AirCompressorMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

/**
 * @author DustW
 **/
public class AirCompressorGui extends KKGui<AirCompressorMenu> {

    private static final ResourceLocation GUI =
            new ResourceLocation(Kitchenkarrot.MOD_ID, "textures/gui/air_compressor.png");

    AirCompressorBlockEntity be;

    public AirCompressorGui(AirCompressorMenu container, Inventory inv, Component name) {
        super(container, inv, name, GUI);
        be = container.blockEntity;
    }

    @Override
    protected void init() {
        super.init();

        addRenderableWidget(new ProgressWidget(this, GUI,
                leftPos + 79, topPos + 34, 176, 14, 24, 17, false,
                be::getMaxProgress, be::getProgress));
        addRenderableWidget(new ProgressWidget(this, GUI,
                leftPos + 93, topPos + 60, 176, 0, 14, 14, true,
                be::getMaxBurnTime, be::getBurnTime));
    }

    @Override
    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }
}
