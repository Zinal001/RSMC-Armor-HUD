package tech.zinals.rsmc_armor_hud.gui.widgets;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import tech.zinals.rsmc_armor_hud.ModConfig;
import tech.zinals.rsmc_armor_hud.gui.GUIUtils;
import tech.zinals.rsmc_armor_hud.gui.RSMCEquipmentSlot;
import tech.zinals.rsmc_armor_hud.gui.RenderData;

public class RSMCStyleArmorWidget implements IArmorRenderWidget {
    @Override
    public ModConfig.ArmorRender renderStyle() {
        return ModConfig.ArmorRender.RSMC;
    }

    @Override
    public void Render(PoseStack poseStack, RenderData renderData)
    {
        int window_width = renderData.MinecraftInstance.getWindow().getGuiScaledWidth();
        int window_height = renderData.MinecraftInstance.getWindow().getGuiScaledHeight();

        int x = 0;
        int y = 0;

        ModConfig.GuiHAlign xAlign = ModConfig.guiHAlign.get();

        switch (xAlign) {
            case Center -> x = (window_width - 75) / 2;
            case Right -> x = window_width - 75;
        }

        ModConfig.GuiVAlign yAlign = ModConfig.guiVAlign.get();

        switch(yAlign) {
            case Middle -> y = (window_height - 100) / 2;
            case Bottom -> y = window_height - 100;
        }

        x += ModConfig.guiXOffset.get();
        y += ModConfig.guiYOffset.get();

        int[] position = new int[] { x, y };

        renderSlot(EquipmentSlot.HEAD, poseStack, position, renderData, 1, 0);
        renderSlot(RSMCEquipmentSlot.CAPE, poseStack, position, renderData, 0, 1);
        renderSlot(RSMCEquipmentSlot.NECK, poseStack, position, renderData, 1, 1);
        renderSlot(RSMCEquipmentSlot.AMMO, poseStack, position, renderData, 2, 1);
        renderSlot(RSMCEquipmentSlot.HAND, poseStack, position, renderData, 0, 2);
        renderSlot(EquipmentSlot.CHEST, poseStack, position, renderData, 1, 2);
        renderSlot(EquipmentSlot.OFFHAND, poseStack, position, renderData, 2, 2);
        renderSlot(EquipmentSlot.LEGS, poseStack, position, renderData, 1, 3);
        renderSlot(EquipmentSlot.MAINHAND, poseStack, position, renderData, 0, 4);
        renderSlot(EquipmentSlot.OFFHAND, poseStack, position, renderData, 1, 4);
        renderSlot(RSMCEquipmentSlot.GLOVES, poseStack, position, renderData, 2, 4);
    }

    protected void renderSlot(EquipmentSlot slot, PoseStack poseStack, int[] position, RenderData renderData, int slotX, int slotY)
    {
        if(!GUIUtils.INSTANCE.IsGuiSlotEnabled(slot))
            return;

        ItemStack stack = renderData.GetStackFromSlot(slot);
        renderSlot(stack, poseStack, position, renderData, slotX, slotY);
    }

    protected void renderSlot(RSMCEquipmentSlot slot, PoseStack poseStack, int[] position, RenderData renderData, int slotX, int slotY)
    {
        if(!GUIUtils.INSTANCE.IsGuiSlotEnabled(slot))
            return;

        ItemStack stack = renderData.GetStackFromSlot(slot);
        renderSlot(stack, poseStack, position, renderData, slotX, slotY);
    }

    protected void renderSlot(ItemStack stack, PoseStack poseStack, int[] position, RenderData renderData, int slotX, int slotY) {
        if(stack.isEmpty())
            return;

        int x = position[0] + (slotX * 25);
        int y = position[1] + (slotY * 20);

        renderData.ItemRenderer.renderGuiItem(stack, x, y);
        renderData.ItemRenderer.renderGuiItemDecorations(renderData.FontRenderer, stack, x, y, null);
    }
}
