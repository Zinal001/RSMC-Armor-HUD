package tech.zinals.rsmc_armor_hud.gui;

import net.minecraft.world.entity.EquipmentSlot;
import tech.zinals.rsmc_armor_hud.ModConfig;

public class GUIUtils
{
    public static final GUIUtils INSTANCE = new GUIUtils();

    private GUIUtils()
    {

    }

    public boolean IsGuiSlotEnabled(EquipmentSlot slot)
    {
        String slotName = "EQ-" + slot.getName();

        return ModConfig.slotVisibility.get(slotName).get();
    }

    public boolean IsGuiSlotEnabled(RSMCEquipmentSlot slot)
    {
        String slotName = "RSMC-" + slot.name();

        return ModConfig.slotVisibility.get(slotName).get();
    }

}
