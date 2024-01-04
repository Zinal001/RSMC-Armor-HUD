package tech.zinals.rsmc_armor_hud;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraftforge.common.ForgeConfigSpec;
import tech.zinals.rsmc_armor_hud.gui.RSMCEquipmentSlot;

import java.util.HashMap;

public class ModConfig {
    public static final ForgeConfigSpec GENERAL_SPEC;

    public static ForgeConfigSpec.BooleanValue overlayEnabled;
    public static ForgeConfigSpec.EnumValue<ArmorRender> guiStyle;

    public static ForgeConfigSpec.EnumValue<GuiHAlign> guiHAlign;
    public static ForgeConfigSpec.EnumValue<GuiVAlign> guiVAlign;

    public static ForgeConfigSpec.DoubleValue guiXOffset;
    public static ForgeConfigSpec.DoubleValue guiYOffset;

    public static ForgeConfigSpec.IntValue guiScale;
    public static ForgeConfigSpec.IntValue bgOpacity;
    public static ForgeConfigSpec.IntValue bgR;
    public static ForgeConfigSpec.IntValue bgG;
    public static ForgeConfigSpec.IntValue bgB;

    public static HashMap<String, ForgeConfigSpec.BooleanValue> slotVisibility = new HashMap<>();

    static {
        ForgeConfigSpec.Builder configBuilder = new ForgeConfigSpec.Builder();
        setupConfig(configBuilder);

        GENERAL_SPEC = configBuilder.build();
    }

    private static void setupConfig(ForgeConfigSpec.Builder builder)
    {
        builder.comment("Settings for RSMC Armor HUD").push("hud");
        overlayEnabled = builder.comment("Is the armor overlay enabled (visible)").define("enabled", true);
        guiStyle = builder.comment("The style of the GUI").defineEnum("gui.style", ArmorRender.RSMC);

        guiHAlign = builder.comment("The horizontal position of the armor slots").defineEnum("gui.halign", GuiHAlign.Right);
        guiVAlign = builder.comment("The vertical position of the armor slots").defineEnum("gui.valign", GuiVAlign.Middle);

        guiXOffset = builder.comment("The horizontal offset").defineInRange("gui.hoffset", 0.0, -9999.0, 9999.0);
        guiYOffset = builder.comment("The vertical offset").defineInRange("gui.voffset", 0.0, -9999.0, 9999.0);

        guiScale = builder.comment("The scale of the UI in percent").defineInRange("gui.scale", 100, 1, 500);


        builder.pop();

        builder.comment("Change visibility of certain armor slots").push("slots");

        for(EquipmentSlot slot : EquipmentSlot.values())
            setupSlotVisibility(slot, builder);

        for(RSMCEquipmentSlot slot : RSMCEquipmentSlot.values())
            setupSlotVisibility(slot, builder);

        builder.pop();

        builder.comment("Background settings").push("background");

        bgOpacity = builder.comment("The background opacity of the UI in percent").defineInRange("opacity", 0, 0, 100);
        bgR = builder.comment("The red component of the background color in percent").defineInRange("red", 100, 0, 100);
        bgG = builder.comment("The green component of the background color in percent").defineInRange("green", 100, 0, 100);
        bgB = builder.comment("The blue component of the background color in percent").defineInRange("blue", 100, 0, 100);

        builder.pop();
    }

    private static void setupSlotVisibility(EquipmentSlot slot, ForgeConfigSpec.Builder builder)
    {
        ForgeConfigSpec.BooleanValue val = builder.comment("Is the " + slot.getName() + " slot visible").define(slot.getName(), true);
        slotVisibility.put("EQ-" + slot.getName(), val);
    }

    private static void setupSlotVisibility(RSMCEquipmentSlot slot, ForgeConfigSpec.Builder builder)
    {
        ForgeConfigSpec.BooleanValue val = builder.comment("Is the " + slot.name().toLowerCase() + " slot visible").define(slot.name().toLowerCase(), true);
        slotVisibility.put("RSMC-" + slot.name(), val);
    }

    public enum ArmorRender
    {
        List,
        SlimList,
        RSMC
    }

    public enum GuiHAlign
    {
        Left,
        Center,
        Right
    }

    public enum GuiVAlign
    {
        Top,
        Middle,
        Bottom
    }

}
