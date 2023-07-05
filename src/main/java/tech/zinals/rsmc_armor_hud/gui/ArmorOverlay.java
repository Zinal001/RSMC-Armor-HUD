package tech.zinals.rsmc_armor_hud.gui;

import com.mod.rsmc.data.RSMCData;
import com.mod.rsmc.data.RSMCDataFunctionsKt;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.client.gui.IIngameOverlay;
import tech.zinals.rsmc_armor_hud.ModConfig;
import tech.zinals.rsmc_armor_hud.gui.widgets.IArmorRenderWidget;
import tech.zinals.rsmc_armor_hud.gui.widgets.ListStyleArmorWidget;
import tech.zinals.rsmc_armor_hud.gui.widgets.RSMCStyleArmorWidget;
import tech.zinals.rsmc_armor_hud.gui.widgets.SlimListStyleArmorWidget;

import java.util.HashMap;

/**
 * Overlay for the armor slots, currently only supports necklace
 */
public class ArmorOverlay implements IIngameOverlay {

    public static final ArmorOverlay INSTANCE = new ArmorOverlay();

    private static final HashMap<ModConfig.ArmorRender, IArmorRenderWidget> ArmorRenderers = new HashMap<>() {{
        put(ModConfig.ArmorRender.List, new ListStyleArmorWidget());
        put(ModConfig.ArmorRender.SlimList, new SlimListStyleArmorWidget());
        put(ModConfig.ArmorRender.RSMC, new RSMCStyleArmorWidget());
    }};

    private IArmorRenderWidget ArmorRenderWidget = null;

    private ItemRenderer itemRenderer;
    private Font fontRenderer;

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

        itemRenderer = instance.getItemRenderer();
        fontRenderer = instance.font;

        if(itemRenderer == null || fontRenderer == null || instance.player == null)
            return;

        RSMCData data;

        if(playerData != null)
            data = playerData;
        else
            data = RSMCDataFunctionsKt.getRsmc((LivingEntity) instance.player);


        RenderData renderData = new RenderData(instance, data);

        if(ArmorRenderWidget == null || ArmorRenderWidget.renderStyle() != ModConfig.guiStyle.get())
            ArmorRenderWidget = ArmorRenderers.get(ModConfig.guiStyle.get());

        poseStack.clear();
        ArmorRenderWidget.Render(poseStack, renderData);
    }
}
