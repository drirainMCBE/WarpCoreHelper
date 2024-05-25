package romo.warpcorehelper.protocol;

import alemiz.stargate.handler.StarGatePacketHandler;
import alemiz.stargate.protocol.StarGatePacket;
import alemiz.stargate.protocol.types.PacketHelper;
import io.netty.buffer.ByteBuf;
import romo.warpcorehelper.WarpCoreHelper;

public class UpdateWarpPacket extends StarGatePacket {

    private String serverName;
    private String warpName;
    private int updateType;

    @Override
    public byte getPacketId() {
        return 30;
    }

    @Override
    public void encodePayload(ByteBuf byteBuf) {
        PacketHelper.writeString(byteBuf, this.serverName);
        PacketHelper.writeString(byteBuf, this.warpName);
        PacketHelper.writeInt(byteBuf, this.updateType);
    }

    @Override
    public void decodePayload(ByteBuf byteBuf) {
        this.serverName = PacketHelper.readString(byteBuf);
        this.warpName = PacketHelper.readString(byteBuf);
        this.updateType = PacketHelper.readInt(byteBuf);
    }

    @Override
    public boolean handle(StarGatePacketHandler handler) {
        WarpCoreHelper.getInstance().getWarpClients().forEach((key, client) -> {
            if(!client.getSessionName().equals(this.serverName)){
                client.sendPacket(this);
            }
        });
        return true;
    }
}
