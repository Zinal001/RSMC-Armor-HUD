package tech.zinals.rsmc_test;

import com.mod.rsmc.data.RSMCData;
import com.mod.rsmc.data.RSMCDataFunctionsKt;
import com.mod.rsmc.packets.GenericPacketHandler;
import com.mod.rsmc.packets.RSMCPacketHandler;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tech.zinals.rsmc_test.gui.ArmorOverlay;

/**
 * Packet handler for the RequestPlayerDataMessage packet.
 */
public final class RequestPlayerDataMessageHandler extends GenericPacketHandler<RequestPlayerDataMessage>
{
    @NotNull
    @Override
    public Class<RequestPlayerDataMessage> getMessageClass() {
        return RequestPlayerDataMessage.messageClass;
    }

    @Override
    public void encode(RequestPlayerDataMessage packet, @NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter(packet, "packet");
        Intrinsics.checkNotNullParameter(buffer, "buffer");
        buffer.writeInt(packet.entityId);

        if(packet.entityData == null)
            buffer.writeBoolean(false);
        else
        {
            buffer.writeBoolean(true);
            CompoundTag nbt = new CompoundTag();
            packet.entityData.writeToNBT(nbt);
            buffer.writeNbt(nbt);
        }
    }

    @Override
    public RequestPlayerDataMessage decode(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter(buffer, "buffer");

        int entityId = buffer.readInt();
        RSMCData data = null;

        if(buffer.readBoolean())
        {
            data = new RSMCData();
            CompoundTag nbt = buffer.readNbt();
            data.readFromNBT(nbt);
        }

        RequestPlayerDataMessage msg = new RequestPlayerDataMessage(entityId);
        msg.entityData = data;

        return msg;
    }

    @Override
    public void handleServer(RequestPlayerDataMessage packet, @Nullable ServerPlayer player) {
        Intrinsics.checkNotNullParameter(packet, "packet");
        Entity entity = Minecraft.getInstance().level.getEntity(packet.entityId);
        if(!(entity instanceof LivingEntity))
            return;

        RSMCData data = RSMCDataFunctionsKt.getRsmc((LivingEntity) entity);

        /* -- Only for debugging purpose */
        var neck = data.getEquipment().getInventory().getNeck();

        String neckItemName = "EMPTY";
        if(!neck.isEmpty())
            neckItemName = neck.getDisplayName().getString() + "_" + neck.getDamageValue();

        RSMCTest.LOGGER.info("Received RequestPlayerDataMessage: " + packet.entityId + ", " + neckItemName);
        /* -- Debug end */

        packet.entityData = data;
        RSMCPacketHandler.INSTANCE.send(PacketDistributor.ALL.noArg(), packet);
        //RSMCPacketHandler.INSTANCE.send(PacketDistributor.ALL.noArg(), new RSMCPlayerDataMessage(player.getId(), data));
    }

    @Override
    public void handleClient(RequestPlayerDataMessage packet) {
        Intrinsics.checkNotNullParameter(packet, "packet");

        if(Minecraft.getInstance().player.getId() != packet.entityId)
        {
            RSMCTest.LOGGER.info("NOT MY ID: " + packet.entityId);
            return;
        }

        if(packet.entityData != null)
        {
            /* -- Only for debugging purpose */
            var neck = packet.entityData.getEquipment().getInventory().getNeck();

            String neckItemName = "EMPTY";
            if(!neck.isEmpty())
                neckItemName = neck.getDisplayName().getString() + "_" + neck.getDamageValue();
            /* -- Debug end */

            ArmorOverlay.INSTANCE.SetData(packet.entityData);
            RSMCDataFunctionsKt.setPlayerRSMC(Minecraft.getInstance().player, packet.entityData);
            //RSMCTest.LOGGER.info("RSMC Set: " + neckItemName);
        }



    }
}
