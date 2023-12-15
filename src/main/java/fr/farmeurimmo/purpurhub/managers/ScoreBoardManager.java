package fr.farmeurimmo.purpurhub.managers;

import fr.farmeurimmo.purpurhub.PurpurHub;
import fr.farmeurimmo.purpurhub.TimeUtils;
import fr.farmeurimmo.purpurhub.dependencies.LuckPermsHook;
import fr.farmeurimmo.users.User;
import fr.farmeurimmo.users.UsersManager;
import fr.mrmicky.fastboard.FastBoard;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class ScoreBoardManager {

    public static ScoreBoardManager INSTANCE;
    private final HashMap<UUID, FastBoard> boards = new HashMap<>();
    private final HashMap<UUID, Integer> boardNum = new HashMap<>();

    public ScoreBoardManager() {
        INSTANCE = this;

        PurpurHub.INSTANCE.getServer().getScheduler().runTaskTimerAsynchronously(PurpurHub.INSTANCE, this::clock, 0, 10);
    }

    public void updateBoard(Player p, int num) {
        switch (num) {
            case 0 -> {
                FastBoard board = boards.get(p.getUniqueId());
                if (board == null) {
                    board = new FastBoard(p);
                }
                board.updateTitle("§6§lPurpurHub");
                board.updateLines(
                        "",
                        "§c...",
                        "",
                        "§a" + TimeUtils.getCurrentTimeAndDate(),
                        "",
                        "§bmc.farmeurimmo.fr");
                boards.put(p.getUniqueId(), board);
                boardNum.put(p.getUniqueId(), num);
            }
            default -> {
                FastBoard board = boards.get(p.getUniqueId());
                if (board == null) {
                    board = new FastBoard(p);
                }
                User user = UsersManager.getCachedUser(p.getUniqueId());
                board.updateTitle("§b§lPurpurHub");
                String supporter = (p.hasPermission("purpurhub.supporter") ? "§a✔" : "§c✕");
                board.updateLines(
                        "",
                        "§e» " + p.getName(),
                        "  §7Prefix §f» §7" + LuckPermsHook.INSTANCE.getPrefix(p.getUniqueId()),
                        "  §7Supporter §f» §7" + supporter,
                        "  §7Fame §f» §c" + (user == null ? "Loading..." : NumberFormat.getInstance().format(user.getFame())),
                        "",
                        "§b" + TimeUtils.getCurrentTimeAndDate(),
                        "",
                        "§9mc.farmeurimmo.fr"
                );
                boards.put(p.getUniqueId(), board);
                boardNum.put(p.getUniqueId(), num);
                break;
            }
        }
    }

    public void clock() {
        ArrayList<UUID> toRemove = new ArrayList<>();
        for (UUID uuid : boards.keySet()) {
            Player p = Bukkit.getPlayer(uuid);
            if (p == null) {
                toRemove.add(uuid);
                continue;
            }
            updateBoard(p, boardNum.get(uuid));
        }
        for (UUID uuid : toRemove) {
            boards.remove(uuid);
            boardNum.remove(uuid);
        }
    }
}
