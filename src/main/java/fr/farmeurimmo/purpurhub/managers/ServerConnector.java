package fr.farmeurimmo.purpurhub.managers;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import fr.farmeurimmo.purpurhub.PurpurHub;
import org.bukkit.entity.Player;

public class ServerConnector {

    public static ServerConnector INSTANCE;

    public ServerConnector() {
        INSTANCE = this;
    }

    public void connect(Player p, String server) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Connect");
        out.writeUTF(server);
        p.sendPluginMessage(PurpurHub.INSTANCE, "BungeeCord", out.toByteArray());
    }
}
