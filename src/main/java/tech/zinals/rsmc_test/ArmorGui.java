package tech.zinals.rsmc_test;

import com.mod.rsmc.container.ContainerLibrary;
import com.mod.rsmc.data.RSMCData;
import com.mod.rsmc.data.RSMCDataFunctionsKt;
import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.slf4j.Logger;

public class ArmorGui extends Gui {

    private static final Logger LOGGER = LogUtils.getLogger();
    private ItemRenderer itemRenderer;
    private Font fontRenderer;
    private final Minecraft minecraftInstance;

    public ArmorGui() {
        super(Minecraft.getInstance());
        minecraftInstance = Minecraft.getInstance();
        itemRenderer = minecraftInstance.getItemRenderer();
        fontRenderer = minecraftInstance.font;
    }

    @SubscribeEvent
    public void onPreRenderGui(RenderGameOverlayEvent.Pre event)
    {
        if(event.getType() != RenderGameOverlayEvent.ElementType.ALL)
            return;

        if(itemRenderer == null)
            itemRenderer = minecraftInstance.getItemRenderer();

        if(fontRenderer == null)
            fontRenderer = minecraftInstance.font;

        if(itemRenderer == null || fontRenderer == null)
            return;

        var player = minecraftInstance.player;
        if(player == null)
            return;

        RSMCData rsmcData = RSMCDataFunctionsKt.getRsmc(player);
        var neck = rsmcData.getEquipment().getInventory().getNeck();

        if(neck.isEmpty())
            return;

        int width = minecraftInstance.getWindow().getGuiScaledWidth();
        int height = minecraftInstance.getWindow().getGuiScaledHeight();

        double durability = ((double) neck.getDamageValue() / neck.getMaxDamage()) * 100;
        durability = 100.0 - (Math.round(durability * 100) / 100.0);

        var mat = event.getMatrixStack();
        mat.clear();

        String durabilityText = durability + "%";

        int textWidth = fontRenderer.width(durabilityText);

        int x = width - 25 - textWidth - 5;
        int y = (height - 25) / 2;

        itemRenderer.renderGuiItem(neck, x, y);
        fontRenderer.draw(mat, durabilityText, x + 25, y + 4, 16777215);
    }
}
