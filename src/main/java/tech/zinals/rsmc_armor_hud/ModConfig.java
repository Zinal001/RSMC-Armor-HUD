package tech.zinals.rsmc_armor_hud;

import net.minecraftforge.common.ForgeConfigSpec;

public class ModConfig {
    public static final ForgeConfigSpec GENERAL_SPEC;

    public static ForgeConfigSpec.BooleanValue overlayEnabled;
    public static ForgeConfigSpec.EnumValue<ArmorRender> guiStyle;

    static {
        ForgeConfigSpec.Builder configBuilder = new ForgeConfigSpec.Builder();
        setupConfig(configBuilder);

        GENERAL_SPEC = configBuilder.build();
    }

    private static void setupConfig(ForgeConfigSpec.Builder builder)
    {
        builder.comment("Settings for RSMC Armor HUD").push("rsmc_armor_hud");
        overlayEnabled = builder.comment("Is the armor overlay enabled (visible)?").define("enabled", true);
        guiStyle = builder.comment("The style of the GUI").defineEnum("gui_style", ArmorRender.RSMC);
        builder.pop();
    }

    public enum ArmorRender
    {
        List,
        SlimList,
        RSMC
    }

}
