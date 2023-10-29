package fr.farmeurimmo.purpurhub.utils;

import com.fastasyncworldedit.core.FaweAPI;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.BuiltInClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardWriter;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.session.ClipboardHolder;
import fr.farmeurimmo.purpurhub.PurpurHub;
import org.bukkit.Location;
import org.bukkit.World;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class FAWEUtils {

    public static FAWEUtils INSTANCE;

    public FAWEUtils() {
        INSTANCE = this;
    }

    private void pasteSchematic(File file, Location pos) {
        try {
            com.sk89q.worldedit.world.World weWorld = FaweAPI.getWorld(pos.getWorld().getName());
            EditSession editSession = WorldEdit.getInstance().getEditSessionFactory().getEditSession(weWorld,
                    -1);

            Clipboard clipboard = FaweAPI.load(file);

            Operation operation = new ClipboardHolder(clipboard).createPaste(editSession)
                    .to(BlockVector3.at(pos.getX(), pos.getY(), pos.getZ())).ignoreAirBlocks(true).copyBiomes(true).build();

            Operations.complete(operation);
            editSession.flushSession();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void pasteSchematic(File file, Location pos, boolean async) {
        if (!async) {
            pasteSchematic(file, pos);
            return;
        }
        CompletableFuture.runAsync(() -> {
            pasteSchematic(file, pos);
        });

    }

    private void saveSchematic(String filename, Location loc1, Location loc2, World world, Location center) {
        try {
            com.sk89q.worldedit.world.World weWorld = BukkitAdapter.adapt(world);
            BlockVector3 pos1 = BlockVector3.at(loc1.getBlockX(), loc1.getBlockY(), loc1.getBlockZ());
            BlockVector3 pos2 = BlockVector3.at(loc2.getBlockX(), loc2.getBlockY(), loc2.getBlockZ());
            Region cReg = new CuboidRegion(weWorld, pos2, pos1);
            File file = new File(PurpurHub.INSTANCE.getDataFolder(), filename + ".schem");
            Clipboard clipboard = Clipboard.create(cReg);
            clipboard.setOrigin(BlockVector3.at(center.getX(), center.getY(), center.getZ()));

            try (ClipboardWriter writer = BuiltInClipboardFormat.FAST.getWriter(new FileOutputStream(file))) {
                writer.write(clipboard);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveSchematic(String filename, Location loc1, Location loc2, Location center, boolean async) {
        if (!async) {
            saveSchematic(filename, loc1, loc2, center.getWorld(), center);
            return;
        }
        CompletableFuture.runAsync(() -> {
            saveSchematic(filename, loc1, loc2, center.getWorld(), center);
        });
    }
}
