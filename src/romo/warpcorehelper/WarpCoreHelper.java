package romo.warpcorehelper;

import alemiz.stargate.StarGate;
import alemiz.stargate.events.ClientDisconnectedEvent;
import alemiz.stargate.server.ServerSession;
import dev.waterdog.waterdogpe.ProxyServer;
import dev.waterdog.waterdogpe.plugin.Plugin;
import romo.warpcorehelper.protocol.UpdateWarpPacket;
import romo.warpcorehelper.protocol.WarpClientConnectPacket;
import romo.warpcorehelper.protocol.WarpRequestPacket;

import java.util.HashMap;

public class WarpCoreHelper extends Plugin {

    private static WarpCoreHelper instance;

    public static WarpCoreHelper getInstance() {
        return instance;
    }

    private final HashMap<String, ServerSession> warpClients = new HashMap<>();

    @Override
    public void onEnable() {
        instance = this;
        StarGate.getInstance().getServer().getProtocolCodec().registerPacket((byte) 29, WarpClientConnectPacket.class);
        StarGate.getInstance().getServer().getProtocolCodec().registerPacket((byte) 30, UpdateWarpPacket.class);
        StarGate.getInstance().getServer().getProtocolCodec().registerPacket((byte) 31, WarpRequestPacket.class);
        ProxyServer.getInstance().getEventManager().subscribe(ClientDisconnectedEvent.class, this::onClientDisconnected);
    }

    public void onWarpClientConnect(String clientName){
        ServerSession serverSession = StarGate.getInstance().getSession(clientName);
        if(serverSession == null){
            return;
        }
        warpClients.put(clientName, serverSession);
    }

    public void onClientDisconnected(ClientDisconnectedEvent event){
        warpClients.remove(event.getSession().getSessionName());
    }

    public HashMap<String, ServerSession> getWarpClients() {
        return warpClients;
    }
}
