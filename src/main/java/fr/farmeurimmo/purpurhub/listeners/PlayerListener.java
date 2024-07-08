package fr.farmeurimmo.purpurhub.listeners;

import fr.farmeurimmo.purpurhub.PurpurHub;
import fr.farmeurimmo.purpurhub.dependencies.LuckPermsHook;
import fr.farmeurimmo.purpurhub.invs.NavigationInv;
import fr.farmeurimmo.purpurhub.managers.ItemManager;
import fr.farmeurimmo.purpurhub.managers.ScoreBoardManager;
import fr.farmeurimmo.purpurhub.managers.TABManager;
import fr.farmeurimmo.users.UsersManager;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {


    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        ScoreBoardManager.INSTANCE.updateBoard(p, -1);
        TABManager.INSTANCE.update(p);

        PurpurHub.INSTANCE.applyHub(p);

        UsersManager.getUserOrCreate(p.getUniqueId(), p.getName()).thenAccept(usr -> {
            //TODO
        }).exceptionally(ex -> {
            ex.printStackTrace();
            p.kick(Component.text("§cAn error occurred while loading your data, please try again later"), PlayerKickEvent.Cause.PLUGIN);
            return null;
        });

        e.joinMessage(Component.text("§6[§a+§6] §f" + LuckPermsHook.INSTANCE.getPlayerWithPrefixAndSuffix(p)));
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();

        e.quitMessage(Component.text("§6[§c-§8] §f" + LuckPermsHook.INSTANCE.getPlayerWithPrefixAndSuffix(p)));
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (e.getItem() == null) return;
        if (e.getItem().equals(ItemManager.INSTANCE.getNavigationItem())) {
            new NavigationInv().open(p);
        }
    }
}
