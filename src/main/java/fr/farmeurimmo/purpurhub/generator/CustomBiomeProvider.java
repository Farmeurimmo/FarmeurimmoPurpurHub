package fr.farmeurimmo.purpurhub.generator;

import fr.farmeurimmo.purpurhub.utils.FastNoiseLiteUtils;
import org.bukkit.block.Biome;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.WorldInfo;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CustomBiomeProvider extends BiomeProvider {

    private final ArrayList<Biome> biomes = new ArrayList<>(
            List.of(Biome.PLAINS,
                    Biome.FOREST,
                    Biome.OCEAN)
    );

    private final FastNoiseLiteUtils biomeNoise = new FastNoiseLiteUtils();

    public CustomBiomeProvider() {
        biomeNoise.SetFrequency(0.0005f);
        biomeNoise.SetFractalType(FastNoiseLiteUtils.FractalType.FBm);
        biomeNoise.SetFractalOctaves(7);
    }

    @Override
    public @NotNull Biome getBiome(@NotNull WorldInfo worldInfo, int x, int y, int z) {
        float noise = biomeNoise.GetNoise(x, z);
        /*if (noise < -0.33) return Biome.COLD_OCEAN;
        if (noise > 0.33) return Biome.WARM_OCEAN;
        return Biome.OCEAN;*/
        if (noise < -0.33) return Biome.PLAINS;
        if (noise > 0.33) return Biome.FOREST;
        return Biome.PLAINS;
    }

    @Override
    public @NotNull List<Biome> getBiomes(@NotNull WorldInfo worldInfo) {
        return biomes;
    }
}
