package fr.farmeurimmo.purpurhub.generator;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.block.Biome;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.LimitedRegion;
import org.bukkit.generator.WorldInfo;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class CustomChunkPopulator extends BlockPopulator {

    private final HashMap<Biome, List<TreeType>> biomeTrees = new HashMap<>() {{
        put(Biome.PLAINS, List.of());
        put(Biome.FOREST, List.of(TreeType.BIRCH));
        put(Biome.DARK_FOREST, List.of(TreeType.DARK_OAK));
    }};

    @Override
    public void populate(@NotNull WorldInfo worldInfo, @NotNull Random random, int chunkX, int chunkZ, @NotNull LimitedRegion limitedRegion) {
        for (int iter = 0; iter < 30; iter++) {
            int x = random.nextInt(16) + chunkX * 16;
            int z = random.nextInt(16) + chunkZ * 16;
            int y = 180;
            while (limitedRegion.getType(x, y, z).isAir() && y > 60) y--;

            Location location = new Location(Bukkit.getWorld(worldInfo.getUID()), x, y + 1, z);
            if (limitedRegion.getType(x, y, z) != Material.GRASS_BLOCK) continue;
            if (limitedRegion.getType(x, y + 1, z) != Material.AIR) continue;

            if (random.nextInt(100) < 60) {
                if (random.nextInt(100) < 20) limitedRegion.setType(x, y + 1, z, Material.FERN);
                else limitedRegion.setType(x, y + 1, z, Material.GRASS);
                continue;
            }

            if (biomeTrees.get(limitedRegion.getBiome(location)).isEmpty()) continue;

            TreeType treeType = biomeTrees.get(limitedRegion.getBiome(location)).get(random.nextInt(biomeTrees.get(limitedRegion.getBiome(location)).size()));

            if (treeType != null) {
                limitedRegion.generateTree(location, random, treeType);
            }
        }
    }

    //function used to make a smooth transition between the two biomes
    private int getPositionFromTheOtherBiome(@NotNull LimitedRegion limitedRegion, int x, int z, int radius) {
        int toReturn = 0;
        for (int i = -radius; i < radius; i++) {
            if (limitedRegion.getBiome(x + i, 0, z).equals(limitedRegion.getBiome(x, 0, z))) toReturn++;
        }
        return toReturn;
    }
}
