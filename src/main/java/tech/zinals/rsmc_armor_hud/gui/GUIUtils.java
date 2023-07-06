package tech.zinals.rsmc_armor_hud.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
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
