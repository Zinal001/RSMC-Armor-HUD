package tech.zinals.rsmc_test;

import com.mod.rsmc.packets.RSMCPacketHandler;
import com.mojang.logging.LogUtils;
import net.minecraft.client.gui.screens.Overlay;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.client.gui.OverlayRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import tech.zinals.rsmc_test.gui.widgets.ArmorOverlay;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.stream.Collectors;

@Mod(RSMCTest.MOD_ID)
public class RSMCTest
{
    public static final String MOD_ID = "rsmc_test";
    public static final Logger LOGGER = LogUtils.getLogger();

    public RSMCTest()
    {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);

        MinecraftForge.EVENT_BUS.register(this);

    }

    /**
     * Common Setup
     * @param event
     */
    private void setup(final FMLCommonSetupEvent event) {

        //Register a custom packet handler for the RequestPlayerDataMessage packet.
        //The idea is that the server should return the latest RSMCData, but it doesn't work properly
        try
        {
            Method RSMCPacketHandler_Register_Method = com.mod.rsmc.packets.RSMCPacketHandler.class.getDeclaredMethod("register", com.mod.rsmc.packets.GenericPacketHandler.class);
            RSMCPacketHandler_Register_Method.setAccessible(true);

            RSMCPacketHandler_Register_Method.invoke(RSMCPacketHandler.INSTANCE, new RequestPlayerDataMessageHandler());
        }
        catch(Exception ex)
        {
            LOGGER.error(ex.getMessage());
        }
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
