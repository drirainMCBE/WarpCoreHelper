package romo.warpcorehelper.protocol;

import alemiz.stargate.handler.StarGatePacketHandler;
import alemiz.stargate.protocol.StarGatePacket;
import alemiz.stargate.protocol.types.PacketHelper;
import alemiz.stargate.server.ServerSession;
import io.netty.buffer.ByteBuf;
import romo.warpcorehelper.WarpCoreHelper;

public class WarpRequestPacket extends StarGatePacket {

    private String serverName;
    private String warpName;
    private String playerName;

    @Override
    public byte getPacketId() {
        return 31;
    }

    @Override
    public void encodePayload(ByteBuf byteBuf) {
        PacketHelper.writeString(byteBuf, this.serverName);
        PacketHelper.writeString(byteBuf, this.warpName);
        PacketHelper.writeString(byteBuf, this.playerName);
    }

    @Override
    public void decodePayload(ByteBuf byteBuf) {
        this.serverName = PacketHelper.readString(byteBuf);
        this.warpName = PacketHelper.readString(byteBuf);
        this.playerName = PacketHelper.readString(byteBuf);
    }

    @Override
    public boolean handle(StarGatePacketHandler handler) {
        ServerSession client = WarpCoreHelper.getInstance().getWarpClients().get(this.serverName);
        if(client != null){
            client.sendPacket(this);
        }
        return true;
    }
}
