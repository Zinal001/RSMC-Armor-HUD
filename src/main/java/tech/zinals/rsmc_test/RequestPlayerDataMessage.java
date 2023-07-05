package tech.zinals.rsmc_test;

import com.mod.rsmc.data.RSMCData;

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
