package tech.zinals.rsmc_armor_hud.gui.widgets;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import tech.zinals.rsmc_armor_hud.ModConfig;
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

        int x = window_width - 75;
        int y = (window_height - 100) / 2;

        int[] position = new int[] { x, y };

        renderSlot(renderData.MinecraftInstance.player.getItemBySlot(EquipmentSlot.HEAD), poseStack, position, renderData, 1, 0);
        renderSlot(renderData.Data.getEquipment().getInventory().getCape(), poseStack, position, renderData, 0, 1);
        renderSlot(renderData.Data.getEquipment().getInventory().getNeck(), poseStack, position, renderData, 1, 1);
        renderSlot(renderData.Data.getEquipment().getInventory().getAmmo(), poseStack, position, renderData, 2, 1);
        renderSlot(renderData.Data.getEquipment().getInventory().getHand(), poseStack, position, renderData, 0, 2);
        renderSlot(renderData.MinecraftInstance.player.getItemBySlot(EquipmentSlot.CHEST), poseStack, position, renderData, 1, 2);
        renderSlot(renderData.MinecraftInstance.player.getItemBySlot(EquipmentSlot.OFFHAND), poseStack, position, renderData, 2, 2);
        renderSlot(renderData.MinecraftInstance.player.getItemBySlot(EquipmentSlot.LEGS), poseStack, position, renderData, 1, 3);
        renderSlot(renderData.MinecraftInstance.player.getItemBySlot(EquipmentSlot.MAINHAND), poseStack, position, renderData, 0, 4);
        renderSlot(renderData.MinecraftInstance.player.getItemBySlot(EquipmentSlot.FEET), poseStack, position, renderData, 1, 4);
        renderSlot(renderData.Data.getEquipment().getInventory().getGloves(), poseStack, position, renderData, 2, 4);
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
