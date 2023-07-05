package tech.zinals.rsmc_armor_hud.gui.widgets;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import tech.zinals.rsmc_armor_hud.ModConfig;
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

    @Override
    public void Render(PoseStack poseStack, RenderData renderData)
    {
        //Slots: Helmet, Armor, Legs, Boots, Main-Hand, Off-Hand, Mantle, Neckless, Ammo, Hand, Gloves

        int window_width = renderData.MinecraftInstance.getWindow().getGuiScaledWidth();
        int window_height = renderData.MinecraftInstance.getWindow().getGuiScaledHeight();

        ArrayList<ItemStack> visibleSlots = new ArrayList<>();
        addIfVisible(renderData.MinecraftInstance.player.getItemBySlot(EquipmentSlot.HEAD), visibleSlots);
        addIfVisible(renderData.MinecraftInstance.player.getItemBySlot(EquipmentSlot.CHEST), visibleSlots);
        addIfVisible(renderData.MinecraftInstance.player.getItemBySlot(EquipmentSlot.LEGS), visibleSlots);
        addIfVisible(renderData.MinecraftInstance.player.getItemBySlot(EquipmentSlot.FEET), visibleSlots);
        addIfVisible(renderData.MinecraftInstance.player.getItemBySlot(EquipmentSlot.MAINHAND), visibleSlots);
        addIfVisible(renderData.MinecraftInstance.player.getItemBySlot(EquipmentSlot.OFFHAND), visibleSlots);
        addIfVisible(renderData.Data.getEquipment().getInventory().getCape(), visibleSlots);
        addIfVisible(renderData.Data.getEquipment().getInventory().getAmmo(), visibleSlots);
        addIfVisible(renderData.Data.getEquipment().getInventory().getHand(), visibleSlots);
        addIfVisible(renderData.Data.getEquipment().getInventory().getNeck(), visibleSlots);
        addIfVisible(renderData.Data.getEquipment().getInventory().getGloves(), visibleSlots);

        int xOffset = GetXOffset(renderData);

        int x = window_width - xOffset;
        int y = (window_height - (20 * visibleSlots.size())) / 2;

        int[] position = new int[] { x, y };

        for (ItemStack stack : visibleSlots)
        {
            renderSlot(stack, poseStack, position, renderData);
        }
    }

    private void addIfVisible(ItemStack itemStack, ArrayList<ItemStack> list)
    {
        if(!itemStack.isEmpty())
            list.add(itemStack);
    }

    protected void renderSlot(ItemStack stack, PoseStack poseStack, int[] position, RenderData renderData)
    {
        if(stack.isEmpty())
            return;

        double durability = ((double) stack.getDamageValue() / stack.getMaxDamage()) * 100;
        durability = 100.0 - (Math.round(durability * 100) / 100.0);

        String durabilityText = durability + "%";

        renderData.ItemRenderer.renderGuiItem(stack, position[0], position[1]);
        renderData.FontRenderer.draw(poseStack, durabilityText, position[0] + 20, position[1] + 4, 16777215);

        position[1] += 20;
    }
}
