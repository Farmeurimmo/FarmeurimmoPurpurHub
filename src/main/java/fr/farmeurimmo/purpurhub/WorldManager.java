package fr.farmeurimmo.purpurhub;

import fr.farmeurimmo.purpurhub.generator.CustomBiomeProvider;
import fr.farmeurimmo.purpurhub.generator.CustomChunkGenerator;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;

public class WorldManager {

    public static final String WORLD_NAME = "hub";
    public static WorldManager INSTANCE;

    public WorldManager() {
        INSTANCE = this;

        removeWorld();
        removeWorld();
        removeWorld();

        Bukkit.getScheduler().runTaskLater(PurpurHub.INSTANCE, this::loadWorld, 40L);
    }

    private void removeWorld() {
        if (getWorld() == null) {
            return;
        }
        if (Bukkit.getWorld("world") == null) return;
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.teleport(Bukkit.getWorld("world").getSpawnLocation());
        }
        Bukkit.unloadWorld(WORLD_NAME, false);
        Bukkit.getWorlds().remove(getWorld());
    }

    public World getWorld() {
        return Bukkit.getWorld(WORLD_NAME);
    }

    private void createWorld() {
        WorldCreator wc = new WorldCreator(WORLD_NAME);
        wc.generator(new CustomChunkGenerator());
        wc.biomeProvider(new CustomBiomeProvider());
        wc.generator();
        wc.createWorld();
    }

    private void loadWorld() {
        World world = getWorld();
        if (world == null) {
            createWorld();
            world = getWorld();
        }
        world.setAutoSave(false);
        world.setSpawnLocation(0, world.getHighestBlockYAt(0, 0) + 2, 0);

        for (Player p : Bukkit.getOnlinePlayers()) {
            p.teleportAsync(world.getSpawnLocation());
        }
    }

}
