package fr.farmeurimmo.purpurhub.managers;

import fr.farmeurimmo.purpurhub.PurpurHub;
import fr.farmeurimmo.purpurhub.TimeUtils;
import fr.farmeurimmo.purpurhub.dependencies.LuckPermsHook;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class TABManager {

    public static TABManager INSTANCE;

    public TABManager() {
        INSTANCE = this;

        PurpurHub.INSTANCE.getServer().getScheduler().runTaskTimerAsynchronously(PurpurHub.INSTANCE, this::clock, 0, 10);
    }

    public void update(Player p) {
        p.sendPlayerListHeaderAndFooter(Component.text("\n§7Hub\n"),
                Component.text("\n§b" + TimeUtils.getCurrentTimeAndDate() + "\n\n§6farmeurimmo.fr\n"));
        p.playerListName(Component.text("§7" + LuckPermsHook.INSTANCE.getPlayerWithPrefixAndSuffix(p)));
    }

    public void clock() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            update(p);
        }
    }
}
