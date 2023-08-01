package fr.farmeurimmo.purpurhub.listeners;

import fr.farmeurimmo.purpurhub.dependencies.LuckPermsHook;
import fr.farmeurimmo.purpurhub.managers.ScoreBoardManager;
import fr.farmeurimmo.purpurhub.managers.TABManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {


    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        ScoreBoardManager.INSTANCE.updateBoard(p, -1);
        TABManager.INSTANCE.update(p);

        e.setJoinMessage("§6[§a+§6] §f" + LuckPermsHook.INSTANCE.getPlayerWithPrefixAndSuffix(p));
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();

        e.setQuitMessage("§6[§c-§8] §f" + LuckPermsHook.INSTANCE.getPlayerWithPrefixAndSuffix(p));
    }
}
