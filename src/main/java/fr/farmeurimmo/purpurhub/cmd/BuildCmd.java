package fr.farmeurimmo.purpurhub.cmd;

import fr.farmeurimmo.purpurhub.PurpurHub;
import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class BuildCmd implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        if (!(sender instanceof Player p)) {
            sender.sendMessage("§cYou must be a player to execute this command !");
            return false;
        }
        if (PurpurHub.INSTANCE.build.contains(p.getUniqueId())) {
            PurpurHub.INSTANCE.build.remove(p.getUniqueId());
            p.sendMessage("§cYou are no longer in build mode !");
            p.sendActionBar(Component.text(" "));
        } else {
            PurpurHub.INSTANCE.build.add(p.getUniqueId());
            p.sendMessage("§aYou are now in build mode !");
            p.sendActionBar(Component.text("§a§lIN BUILD MODE"));
        }
        return false;
    }
}
