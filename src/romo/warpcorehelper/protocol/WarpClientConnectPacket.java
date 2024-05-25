package romo.warpcorehelper.protocol;

import alemiz.stargate.handler.StarGatePacketHandler;
import alemiz.stargate.protocol.StarGatePacket;
import alemiz.stargate.protocol.types.PacketHelper;
import io.netty.buffer.ByteBuf;
import romo.warpcorehelper.WarpCoreHelper;

public class WarpClientConnectPacket extends StarGatePacket {

    private String clientName;

    @Override
    public byte getPacketId() {
        return 29;
    }

    @Override
    public void encodePayload(ByteBuf byteBuf) {
        //NOTHING
    }

    @Override
    public void decodePayload(ByteBuf byteBuf) {
        this.clientName = PacketHelper.readString(byteBuf);
    }

    @Override
    public boolean handle(StarGatePacketHandler handler) {
        WarpCoreHelper.getInstance().onWarpClientConnect(this.clientName);
        return true;
    }
}
