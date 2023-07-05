package tech.zinals.rsmc_test;

import com.mod.rsmc.data.RSMCData;
import com.mod.rsmc.data.RSMCDataFunctionsKt;
import com.mod.rsmc.packets.RSMCPacketHandler;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import org.apache.http.annotation.Obsolete;
import org.slf4j.Logger;

import java.util.Date;

@Obsolete
public class InventoryEvents {

    private String LastNeckItemName = null;
    private final Logger LOGGER;

    private Date LastServerUpdate = new Date();

    public InventoryEvents(Logger logger)
    {
        LOGGER = logger;
    }

    @SubscribeEvent
    public void onInventory(TickEvent.PlayerTickEvent event)
    {
        if(event.phase.equals(TickEvent.Phase.START) || event.side.equals(LogicalSide.SERVER))
            return;

        /*Date current = new Date();

        long diff = Math.abs(current.getTime() - LastServerUpdate.getTime());

        if(diff >= 2500)
        {
            RSMCPacketHandler.INSTANCE.sendToServer(new RequestPlayerDataMessage(event.player.getId()));
            LastServerUpdate = current;
            LOGGER.info("Server updated?");
        }*/


        RSMCData rsmcData = RSMCDataFunctionsKt.getRsmc(event.player);

        var neck = rsmcData.getEquipment().getInventory().getNeck();
        //var ring = rsmcData.getEquipment().getInventory().getItem(3); //Is this the correct slot? Not sure

        String neckItemName = "EMPTY";
        if(!neck.isEmpty())
            neckItemName = neck.getDisplayName().getString() + "_" + neck.getDamageValue();

        if(!neckItemName.equals(LastNeckItemName))
        {
            LastNeckItemName = neckItemName;
            LOGGER.info("Neck changed to: " + neckItemName);
        }
    }
}
