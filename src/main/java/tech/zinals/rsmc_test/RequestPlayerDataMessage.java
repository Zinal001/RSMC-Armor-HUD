package tech.zinals.rsmc_test;

import com.mod.rsmc.data.RSMCData;
import com.mod.rsmc.data.RSMCDataFunctionsKt;
import com.mod.rsmc.packets.GenericPacketHandler;
import com.mod.rsmc.packets.RSMCPacketHandler;
import com.mod.rsmc.packets.RSMCPlayerDataMessage;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A packet that is sent from both the server and the client.
 * When sent from to client (to the server) it only contains the entityId.
 * When sent from the server (to a client) it contains both the entityId and the latest entityData.
 */
public final class RequestPlayerDataMessage
{
    public static final Class<RequestPlayerDataMessage> messageClass = RequestPlayerDataMessage.class;

    public final int entityId;
    public RSMCData entityData;

    public RequestPlayerDataMessage(int entityId)
    {
        this.entityId = entityId;
    }
}
