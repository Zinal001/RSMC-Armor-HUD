package tech.zinals.rsmc_armor_hud.gui.widgets;

import com.mojang.blaze3d.vertex.PoseStack;
import tech.zinals.rsmc_armor_hud.ModConfig;
import tech.zinals.rsmc_armor_hud.gui.RenderData;

public interface IArmorRenderWidget {

    ModConfig.ArmorRender renderStyle();

    void Render(PoseStack poseStack, RenderData renderData);

}

