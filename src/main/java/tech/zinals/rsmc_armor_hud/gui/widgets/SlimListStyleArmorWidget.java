package tech.zinals.rsmc_armor_hud.gui.widgets;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.item.ItemStack;
import tech.zinals.rsmc_armor_hud.ModConfig;
import tech.zinals.rsmc_armor_hud.gui.RenderData;

public class SlimListStyleArmorWidget extends ListStyleArmorWidget{

    @Override
    public ModConfig.ArmorRender renderStyle() {
        return ModConfig.ArmorRender.SlimList;
    }

    @Override
    protected int GetXOffset(RenderData renderData) {
        return 25;
    }

    @Override
    protected void renderSlot(ItemStack stack, PoseStack poseStack, int[] position, RenderData renderData) {
        if(stack.isEmpty())
            return;

        renderData.ItemRenderer.renderGuiItem(stack, position[0], position[1]);
        renderData.ItemRenderer.renderGuiItemDecorations(renderData.FontRenderer, stack, position[0], position[1], null);

        position[1] += this.GetYOffset(renderData);
    }
}
