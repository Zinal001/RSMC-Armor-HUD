package tech.zinals.rsmc_armor_hud.gui.widgets;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.gui.GuiUtils;
import tech.zinals.rsmc_armor_hud.ModConfig;
import tech.zinals.rsmc_armor_hud.gui.GUIUtils;
import tech.zinals.rsmc_armor_hud.gui.RSMCEquipmentSlot;
import tech.zinals.rsmc_armor_hud.gui.RenderData;

import java.util.ArrayList;

public class ListStyleArmorWidget implements IArmorRenderWidget
{
    @Override
    public ModConfig.ArmorRender renderStyle() {
        return ModConfig.ArmorRender.List;
    }

    protected int GetXOffset(RenderData renderData)
    {
        return renderData.FontRenderer.width("100%") + 30;
    }

    protected int GetYOffset(RenderData renderData)
    {
        return 20;
    }

    @Override
    public void Render(PoseStack poseStack, RenderData renderData)
    {
        float scale = ModConfig.guiScale.get() / 100F;

        //Slots: Helmet, Armor, Legs, Boots, Main-Hand, Off-Hand, Mantle, Neckless, Ammo, Hand, Gloves

        int window_width = renderData.MinecraftInstance.getWindow().getGuiScaledWidth();
        int window_height = renderData.MinecraftInstance.getWindow().getGuiScaledHeight();

        ArrayList<ItemStack> visibleSlots = new ArrayList<>();

        addIfVisible(EquipmentSlot.HEAD, renderData, visibleSlots);
        addIfVisible(EquipmentSlot.CHEST, renderData, visibleSlots);
        addIfVisible(EquipmentSlot.LEGS, renderData, visibleSlots);
        addIfVisible(EquipmentSlot.FEET, renderData, visibleSlots);
        addIfVisible(EquipmentSlot.MAINHAND, renderData, visibleSlots);
        addIfVisible(EquipmentSlot.OFFHAND, renderData, visibleSlots);

        addIfVisible(RSMCEquipmentSlot.CAPE, renderData, visibleSlots);
        addIfVisible(RSMCEquipmentSlot.AMMO, renderData, visibleSlots);
        addIfVisible(RSMCEquipmentSlot.HAND, renderData, visibleSlots);
        addIfVisible(RSMCEquipmentSlot.NECK, renderData, visibleSlots);
        addIfVisible(RSMCEquipmentSlot.GLOVES, renderData, visibleSlots);

        int xOffset = Math.round(GetXOffset(renderData) * scale);
        int yOffset = Math.round(GetYOffset(renderData) * scale);

        int x = 0;
        int y = 0;

        int containerHeight = yOffset * visibleSlots.size();

        ModConfig.GuiHAlign xAlign = ModConfig.guiHAlign.get();

        switch (xAlign) {
            case Center -> x = (window_width - xOffset) / 2;
            case Right -> x = window_width - xOffset;
        }

        ModConfig.GuiVAlign yAlign = ModConfig.guiVAlign.get();

        switch(yAlign) {
            case Middle -> y = (window_height - containerHeight) / 2;
            case Bottom -> y = window_height - containerHeight;
        }

        x += ModConfig.guiXOffset.get();
        y += ModConfig.guiYOffset.get();

        GUIUtils.ScaleContext.Run(x, y, scale, () -> {
            int[] position = new int[] { 0, 0 };
            for (ItemStack stack : visibleSlots)
            {
                renderSlot(stack, poseStack, position, renderData);
            }
        });
    }

    private void addIfVisible(EquipmentSlot slot, RenderData renderData, ArrayList<ItemStack> list)
    {
        if(!GUIUtils.INSTANCE.IsGuiSlotEnabled(slot))
            return;

        ItemStack stack = renderData.GetStackFromSlot(slot);

        if(stack.isEmpty())
            return;

        list.add(stack);
    }

    private void addIfVisible(RSMCEquipmentSlot slot, RenderData renderData, ArrayList<ItemStack> list)
    {
        if(!GUIUtils.INSTANCE.IsGuiSlotEnabled(slot))
            return;

        ItemStack stack = renderData.GetStackFromSlot(slot);

        if(stack.isEmpty())
            return;

        list.add(stack);
    }

    protected void renderSlot(ItemStack stack, PoseStack poseStack, int[] position, RenderData renderData)
    {
        if(stack.isEmpty())
            return;

        double durability = ((double) stack.getDamageValue() / stack.getMaxDamage()) * 100;
        durability = 100.0 - (Math.round(durability * 100) / 100.0);

        String durabilityText = durability + "%";


        GUIUtils.INSTANCE.DrawSlotBg(position[0], position[1], renderData, poseStack);
        renderData.ItemRenderer.renderGuiItem(stack, position[0], position[1]);
        renderData.FontRenderer.draw(poseStack, durabilityText, position[0] + 20, position[1] + 4, 16777215);

        position[1] += GetYOffset(renderData);
    }
}
