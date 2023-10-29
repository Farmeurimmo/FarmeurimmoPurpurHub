package fr.farmeurimmo.purpurhub.generator;

import fr.farmeurimmo.purpurhub.utils.FastNoiseLiteUtils;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.WorldInfo;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class CustomChunkGenerator extends ChunkGenerator {

    private final FastNoiseLiteUtils terrainNoise = new FastNoiseLiteUtils();
    private final FastNoiseLiteUtils detailNoise = new FastNoiseLiteUtils();

    private final HashMap<Integer, List<Material>> layers = new HashMap<>() {{
        put(0, List.of(Material.GRASS_BLOCK));
        put(1, List.of(Material.DIRT));
        put(2, List.of(Material.COAL_ORE, Material.IRON_ORE, Material.REDSTONE_ORE, Material.LAPIS_ORE, Material.GOLD_ORE, Material.DIAMOND_ORE));
    }};

    public CustomChunkGenerator() {
        // Set frequencies, lower frequency = slower change.
        terrainNoise.SetFrequency(0.0005f);
        detailNoise.SetFrequency(0.01f);

        // Fractal pattern (optional).
        terrainNoise.SetFractalType(FastNoiseLiteUtils.FractalType.FBm);
        terrainNoise.SetFractalOctaves(7);
    }

    @Override
    public void generateNoise(@NotNull WorldInfo worldInfo, @NotNull Random random, int chunkX, int chunkZ, ChunkData chunkData) {

        for (int y = chunkData.getMinHeight(); y < chunkData.getMaxHeight(); y++) {
            for (int x = 0; x < 16; x++) {
                for (int z = 0; z < 16; z++) {
                    if (y == chunkData.getMinHeight()) {
                        chunkData.setBlock(x, y, z, Material.BEDROCK);
                        continue;
                    }

                    float noise2 = (terrainNoise.GetNoise(x + (chunkX * 16), z + (chunkZ * 16)) * 2) + (detailNoise.GetNoise(x + (chunkX * 16), z + (chunkZ * 16)) / 10);
                    float noise3 = detailNoise.GetNoise(x + (chunkX * 16), y, z + (chunkZ * 16));
                    float currentY = (65 + (noise2 * 20));

                    if (y < currentY) {
                        float distanceToSurface = Math.abs(y - currentY); // The absolute y distance to the world surface.
                        double function = .1 * Math.pow(distanceToSurface, 2) - 1; // A second grade polynomial offset to the noise max and min (1, -1).

                        if (noise3 > Math.min(function, -0.5)) {
                            // Set grass if the block closest to the surface.
                            if (distanceToSurface < 1 && y > 61) {
                                chunkData.setBlock(x, y, z, layers.get(0).get(0));
                            }

                            // It is not the closest block to the surface but still very close.
                            else if (distanceToSurface < 3) {
                                if (y < 62) chunkData.setBlock(x, y, z, Material.SAND);
                                else
                                    chunkData.setBlock(x, y, z, layers.get(1).get(random.nextInt(layers.get(1).size())));
                            }

                            // Not close to the surface at all.
                            else {
                                Material neighbour = Material.STONE;
                                List<Material> neighbourBlocks = new ArrayList<>(Arrays.asList(chunkData.getType(Math.max(x - 1, 0), y, z), chunkData.getType(x, Math.max(y - 1, 0), z), chunkData.getType(x, y, Math.max(z - 1, 0)))); // A list of all neighbour blocks.

                                // Randomly place vein anchors.
                                if (random.nextFloat() < 0.002) {
                                    neighbour = layers.get(2).get(Math.min(random.nextInt(layers.get(2).size()), random.nextInt(layers.get(2).size()))); // A basic way to shift probability to lower values.
                                }

                                // If the current block has an ore block as neighbour, try the current block.
                                if ((!Collections.disjoint(neighbourBlocks, layers.get(2)))) {
                                    for (Material neighbourBlock : neighbourBlocks) {
                                        if (layers.get(2).contains(neighbourBlock) && random.nextFloat() < -0.01 * layers.get(2).indexOf(neighbourBlock) + 0.4) {
                                            neighbour = neighbourBlock;
                                        }
                                    }
                                }

                                chunkData.setBlock(x, y, z, neighbour);
                            }
                        }
                    } else if (y < 62) {
                        chunkData.setBlock(x, y, z, Material.WATER);
                    }
                }
            }
        }
    }

    @Override
    public @NotNull List<BlockPopulator> getDefaultPopulators(@NotNull World world) {
        List<BlockPopulator> populators = new ArrayList<>();
        populators.add(new CustomChunkPopulator());

        return populators;
    }
}
