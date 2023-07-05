package tech.zinals.rsmc_armor_hud;

import com.mojang.logging.LogUtils;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.client.gui.OverlayRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import tech.zinals.rsmc_armor_hud.gui.ArmorOverlay;

@Mod(RSMCArmorHud.MOD_ID)
public class RSMCArmorHud
{
    public static final String MOD_ID = "rsmc_armor_hud";
    public static final Logger LOGGER = LogUtils.getLogger();

    public RSMCArmorHud()
    {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);

        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, tech.zinals.rsmc_armor_hud.ModConfig.GENERAL_SPEC, "rsmc_armor_hud.toml");

        MinecraftForge.EVENT_BUS.register(this);

    }

    /**
     * Client Setup
     * @param event
     */
    private void clientSetup(@NotNull final FMLClientSetupEvent event)
    {
        OverlayRegistry.registerOverlayBelow(ForgeIngameGui.ITEM_NAME_ELEMENT, "rsmc_armor_overlay", ArmorOverlay.INSTANCE);
        OverlayRegistry.enableOverlay(ArmorOverlay.INSTANCE, true);
    }
}
