package tech.zinals.rsmc_test.gui;

import com.mod.rsmc.data.RSMCData;
import com.mod.rsmc.data.RSMCDataFunctionsKt;
import com.mod.rsmc.packets.RSMCPacketHandler;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.client.gui.IIngameOverlay;
import tech.zinals.rsmc_test.RequestPlayerDataMessage;

import java.util.Date;

/**
 * Overlay for the armor slots, currently only supports necklace
 */
public class ArmorOverlay implements IIngameOverlay {

    public static final ArmorOverlay INSTANCE = new ArmorOverlay();

    private ItemRenderer itemRenderer;
    private Font fontRenderer;

    private Date LastServerUpdate = new Date();

    private RSMCData playerData;

    public ArmorOverlay()
    {

    }

    public void SetData(RSMCData data)
    {
        playerData = data;
    }

    @Override
    public void render(ForgeIngameGui gui, PoseStack poseStack, float partialTick, int width, int height)
    {
        Minecraft instance = Minecraft.getInstance();

        requestUpdate(instance.player);

        itemRenderer = instance.getItemRenderer();
        fontRenderer = instance.font;

        if(itemRenderer == null || fontRenderer == null || instance.player == null)
            return;

        RSMCData data = null;

        if(playerData != null)
            data = playerData;
        else
            data = RSMCDataFunctionsKt.getRsmc((LivingEntity) instance.player);

        renderNeck(instance, poseStack, data);
    }

    private void requestUpdate(LivingEntity player)
    {
        Date current = new Date();

        long diff = Math.abs(current.getTime() - LastServerUpdate.getTime());

        //Limit the amount of packets sent to the server.
        //An update will only be sent every ~2.5 seconds.
        //Sadly, the packet doesn't seem to do anything since the server returns the exact same data for the equipment..
        if(diff >= 2500)
        {
            LastServerUpdate = current;
            RSMCPacketHandler.INSTANCE.sendToServer(new RequestPlayerDataMessage(player.getId()));
        }
    }

    private void renderNeck(Minecraft minecraft, PoseStack poseStack, RSMCData data)
    {
        var neck = data.getEquipment().getInventory().getNeck();
        if(neck.isEmpty())
            return;

        int width = minecraft.getWindow().getGuiScaledWidth();
        int height = minecraft.getWindow().getGuiScaledHeight();

        double durability = ((double) neck.getDamageValue() / neck.getMaxDamage()) * 100;
        durability = 100.0 - (Math.round(durability * 100) / 100.0);

        String durabilityText = durability + "%";

        int textWidth = fontRenderer.width(durabilityText);

        int x = width - 25 - textWidth - 5;
        int y = (height - 25) / 2;

        poseStack.clear();
        itemRenderer.renderGuiItem(neck, x, y);
        fontRenderer.draw(poseStack, durabilityText, x + 25, y + 4, 16777215);
    }
}
