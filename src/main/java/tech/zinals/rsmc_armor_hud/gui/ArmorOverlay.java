package tech.zinals.rsmc_armor_hud.gui;

import com.mod.rsmc.data.RSMCData;
import com.mod.rsmc.data.RSMCDataFunctionsKt;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.resources.ResourceLocation;
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
    public static final ResourceLocation WHITE_BG_TEXTURE = new ResourceLocation("minecraft", "textures/misc/white.png");

    private static boolean _WhiteBgBound = false;

    private static final HashMap<ModConfig.ArmorRender, IArmorRenderWidget> ArmorRenderers = new HashMap<>() {{
        put(ModConfig.ArmorRender.List, new ListStyleArmorWidget());
        put(ModConfig.ArmorRender.SlimList, new SlimListStyleArmorWidget());
        put(ModConfig.ArmorRender.RSMC, new RSMCStyleArmorWidget());
    }};

    private IArmorRenderWidget ArmorRenderWidget = null;

    public ArmorOverlay()
    {

    }

    @Override
    public void render(ForgeIngameGui gui, PoseStack poseStack, float partialTick, int width, int height)
    {
        Minecraft instance = Minecraft.getInstance();

        if(instance.getItemRenderer() == null || instance.font == null || instance.player == null)
            return;

        if(!_WhiteBgBound)
        {
            instance.getTextureManager().bindForSetup(WHITE_BG_TEXTURE);
            _WhiteBgBound = true;
        }

        RSMCData data = RSMCDataFunctionsKt.getRsmc(instance.player);

        RenderData renderData = new RenderData(instance, data);

        if(ArmorRenderWidget == null || ArmorRenderWidget.renderStyle() != ModConfig.guiStyle.get())
            ArmorRenderWidget = ArmorRenderers.get(ModConfig.guiStyle.get());

        poseStack.clear();
        ArmorRenderWidget.Render(poseStack, renderData);
        poseStack.clear();
        RenderSystem.getModelViewStack().clear();
    }
}
