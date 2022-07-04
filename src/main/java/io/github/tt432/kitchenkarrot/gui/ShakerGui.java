package io.github.tt432.kitchenkarrot.gui;

import io.github.tt432.kitchenkarrot.Kitchenkarrot;
import io.github.tt432.kitchenkarrot.gui.base.KKGui;
import io.github.tt432.kitchenkarrot.menu.ShakerMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

/**
 * @author DustW
 **/
public class ShakerGui extends KKGui<ShakerMenu> {
    public ShakerGui(ShakerMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle,
                new ResourceLocation(Kitchenkarrot.MOD_ID, "textures/gui/shaker.png"));
    }
}
