package tech.zinals.rsmc_armor_hud.gui;


import com.mod.rsmc.data.RSMCData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

public class RenderData
{
    public Minecraft MinecraftInstance;
    public net.minecraft.client.renderer.entity.ItemRenderer ItemRenderer;
    public Font FontRenderer;
    public RSMCData Data;

    public RenderData(Minecraft minecraftInstance, RSMCData data)
    {
        MinecraftInstance = minecraftInstance;
        ItemRenderer = MinecraftInstance.getItemRenderer();
        FontRenderer = MinecraftInstance.font;
        Data = data;
    }

    public ItemStack GetStackFromSlot(EquipmentSlot slot)
    {
        return MinecraftInstance.player.getItemBySlot(slot);
    }

    public ItemStack GetStackFromSlot(RSMCEquipmentSlot slot)
    {
        return Data.getEquipment().getInventory().getItem(slot.ordinal());
    }
}