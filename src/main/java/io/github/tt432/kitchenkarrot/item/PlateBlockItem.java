package io.github.tt432.kitchenkarrot.item;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@ParametersAreNonnullByDefault
public class PlateBlockItem extends BlockItem {

    public PlateBlockItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(itemStack, level, tooltip, flag);
        tooltip.add(new TranslatableComponent("info.kitchenkarrot.text1"));
        tooltip.add(new TranslatableComponent("info.kitchenkarrot.text2"));
        tooltip.add(new TranslatableComponent("info.kitchenkarrot.text3"));
        if (level != null) {
            if (Screen.hasShiftDown()) {
                PlateItem.showPlateRecipeList(tooltip);
            } else {
                tooltip.add(new TranslatableComponent("info.kitchenkarrot.text4"));
            }
        }
    }
}
