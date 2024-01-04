package tech.zinals.rsmc_armor_hud.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.entity.EquipmentSlot;
import tech.zinals.rsmc_armor_hud.ModConfig;

public class GUIUtils
{
    public static final GUIUtils INSTANCE = new GUIUtils();

    private GUIUtils()
    {

    }

    public boolean IsGuiSlotEnabled(EquipmentSlot slot)
    {
        String slotName = "EQ-" + slot.getName();

        return ModConfig.slotVisibility.get(slotName).get();
    }

    public boolean IsGuiSlotEnabled(RSMCEquipmentSlot slot)
    {
        String slotName = "RSMC-" + slot.name();

        return ModConfig.slotVisibility.get(slotName).get();
    }

    public void DrawSlotBg(int x, int y, RenderData renderData, PoseStack poseStack)
    {
        if(ModConfig.bgOpacity.get() > 0)
        {
            RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
            RenderSystem.setShaderTexture(0, ArmorOverlay.WHITE_BG_TEXTURE);
            RenderSystem.setShaderColor(ModConfig.bgR.get() / 100F, ModConfig.bgG.get() / 100F, ModConfig.bgB.get() / 100F, ModConfig.bgOpacity.get() / 100F);
            RenderSystem.bindTexture(renderData.MinecraftInstance.getTextureManager().getTexture(ArmorOverlay.WHITE_BG_TEXTURE).getId());
            RenderSystem.enableTexture();
            renderData.MinecraftInstance.gui.blit(poseStack, x - 2, y - 2, 0, 0, 20, 20);
            RenderSystem.disableTexture();
        }
    }

    public static class ScaleContext
    {
        public static void Run(int position_x, int position_y, float scale, Runnable func)
        {
            PoseStack poseStack = RenderSystem.getModelViewStack();
            poseStack.pushPose();
            poseStack.translate(position_x, position_y, 100D);
            RenderSystem.applyModelViewMatrix();
            poseStack.scale(scale, scale, 0F);
            RenderSystem.applyModelViewMatrix();

            func.run();

            poseStack.popPose();
            poseStack.clear();
            RenderSystem.applyModelViewMatrix();
        }
    }
}
