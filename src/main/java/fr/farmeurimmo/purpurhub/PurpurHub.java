package fr.farmeurimmo.purpurhub;

import fr.farmeurimmo.purpurhub.cmd.BuildCmd;
import fr.farmeurimmo.purpurhub.dependencies.LuckPermsHook;
import fr.farmeurimmo.purpurhub.listeners.PlayerListener;
import fr.farmeurimmo.purpurhub.listeners.ProtectionListener;
import fr.farmeurimmo.purpurhub.managers.ScoreBoardManager;
import fr.farmeurimmo.purpurhub.managers.TABManager;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public final class PurpurHub extends JavaPlugin {

    public static PurpurHub INSTANCE;
    public static ConsoleCommandSender CONSOLE;
    public ArrayList<UUID> build = new ArrayList<>();

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
        getCommand("build").setExecutor(new BuildCmd());

        CONSOLE.sendMessage("§aRegistering listeners...");
        getServer().getPluginManager().registerEvents(new PlayerListener(), INSTANCE);
        getServer().getPluginManager().registerEvents(new ProtectionListener(), INSTANCE);

        CONSOLE.sendMessage("§6Starting startup tasks...");
        applyHub();
        for (Player p : Bukkit.getOnlinePlayers()) {
            ScoreBoardManager.INSTANCE.updateBoard(p, -1);
            TABManager.INSTANCE.update(p);
        }
        getServer().getScheduler().runTaskTimer(INSTANCE, () -> {
            ArrayList<UUID> toRemove = new ArrayList<>();
            for (UUID uuid : build) {
                Player p = Bukkit.getPlayer(uuid);
                if (p == null) toRemove.add(uuid);
                else {
                    if (!p.hasPermission("purpur.build")) toRemove.add(uuid);
                    else p.sendActionBar("§a§lIN BUILD MODE");
                }
            }
            build.removeAll(toRemove);
        }, 0, 10);

        CONSOLE.sendMessage("§a§lPurpurHub started in " + (System.currentTimeMillis() - startTime) + "ms !");
    }

    @Override
    public void onDisable() {
        long startTime = System.currentTimeMillis();
        CONSOLE.sendMessage("§6Disabling PurpurHub...");

        CONSOLE.sendMessage("§a§lPurpurHub disabled in " + (System.currentTimeMillis() - startTime) + "ms !");
    }

    public void applyHub() {
        for (World world : Bukkit.getWorlds()) {
            for (org.bukkit.entity.Entity entity : world.getEntities()) {
                if (!(entity instanceof org.bukkit.entity.Player)) {
                    entity.remove();
                }
            }
            world.setSpawnLocation(0, 100, 0);
            world.setPVP(true);
            world.setStorm(false);
            world.setThundering(false);
            world.setWeatherDuration(0);
            world.setTime(6000);
            world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
            world.setGameRule(GameRule.DO_WEATHER_CYCLE, false);
            world.setGameRule(GameRule.DO_MOB_SPAWNING, false);
            world.setGameRule(GameRule.DO_FIRE_TICK, false);
            world.setGameRule(GameRule.DO_ENTITY_DROPS, false);
            world.setGameRule(GameRule.DO_TILE_DROPS, false);
            world.setGameRule(GameRule.DO_MOB_LOOT, false);
            world.setGameRule(GameRule.KEEP_INVENTORY, true);
            world.setGameRule(GameRule.DO_IMMEDIATE_RESPAWN, true);
            world.setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false);
            world.setGameRule(GameRule.SHOW_DEATH_MESSAGES, false);
            world.setGameRule(GameRule.SPECTATORS_GENERATE_CHUNKS, false);
            world.setGameRule(GameRule.SEND_COMMAND_FEEDBACK, false);
            world.setGameRule(GameRule.REDUCED_DEBUG_INFO, true);
            world.setGameRule(GameRule.LOG_ADMIN_COMMANDS, false);
            world.setGameRule(GameRule.DISABLE_RAIDS, true);
            world.setGameRule(GameRule.DO_PATROL_SPAWNING, false);
            world.setGameRule(GameRule.RANDOM_TICK_SPEED, 0);
            world.setGameRule(GameRule.SPAWN_RADIUS, 0);
            world.setGameRule(GameRule.MAX_ENTITY_CRAMMING, 0);
            world.setGameRule(GameRule.MOB_GRIEFING, false);
            world.setGameRule(GameRule.NATURAL_REGENERATION, false);
            world.setGameRule(GameRule.DO_TRADER_SPAWNING, false);
            world.setGameRule(GameRule.DO_INSOMNIA, false);
            world.setGameRule(GameRule.FALL_DAMAGE, false);
            world.setGameRule(GameRule.FIRE_DAMAGE, false);
            world.setGameRule(GameRule.FREEZE_DAMAGE, false);
        }
    }
}
