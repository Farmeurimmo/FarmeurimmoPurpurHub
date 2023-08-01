package fr.farmeurimmo.purpurhub;

import fr.farmeurimmo.purpurhub.dependencies.LuckPermsHook;
import fr.farmeurimmo.purpurhub.listeners.PlayerListener;
import fr.farmeurimmo.purpurhub.managers.ScoreBoardManager;
import fr.farmeurimmo.purpurhub.managers.TABManager;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Date;

public final class PurpurHub extends JavaPlugin {

    public static PurpurHub INSTANCE;
    public static ConsoleCommandSender CONSOLE;

    //Not a supporter : ✕
    //A supporter : ✔

    @Override
    public void onEnable() {
        long startTime = new Date().getTime();

        INSTANCE = this;
        CONSOLE = getServer().getConsoleSender();

        CONSOLE.sendMessage("§6Loading PurpurHub...");

        CONSOLE.sendMessage("§aLoading dependencies...");
        new LuckPermsHook();

        CONSOLE.sendMessage("§aRegistering managers...");
        new TABManager();
        new ScoreBoardManager();

        CONSOLE.sendMessage("§aRegistering commands...");

        CONSOLE.sendMessage("§aRegistering listeners...");
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);

        CONSOLE.sendMessage("§6Starting startup tasks...");
        for (Player p : Bukkit.getOnlinePlayers()) {
            ScoreBoardManager.INSTANCE.updateBoard(p, -1);
            TABManager.INSTANCE.update(p);
        }

        CONSOLE.sendMessage("§a§lPurpurHub started in " + (System.currentTimeMillis() - startTime) + "ms !");
    }

    @Override
    public void onDisable() {
        long startTime = System.currentTimeMillis();
        CONSOLE.sendMessage("§6Disabling PurpurHub...");

        CONSOLE.sendMessage("§a§lPurpurHub disabled in " + (System.currentTimeMillis() - startTime) + "ms !");
    }
}
