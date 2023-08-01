package fr.farmeurimmo.purpurhub.dependencies;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.util.UUID;

public class LuckPermsHook {

    public static LuckPermsHook INSTANCE;
    private final net.luckperms.api.LuckPerms luckPermsAPI;

    public LuckPermsHook() {
        INSTANCE = this;

        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(net.luckperms.api.LuckPerms.class);
        assert provider != null;
        luckPermsAPI = provider.getProvider();
        System.out.println("LuckPermsAPI loaded.");
    }

    public String getPrefix(UUID uuid) {
        User user = luckPermsAPI.getUserManager().getUser(uuid);
        String prefix = user != null ? user.getCachedData().getMetaData().getPrefix() : "";
        return prefix != null ? prefix.replace("&", "§") : "";
    }

    public String getSuffix(UUID uuid) {
        User user = luckPermsAPI.getUserManager().getUser(uuid);
        String suffix = user != null ? user.getCachedData().getMetaData().getSuffix() : "";
        return suffix != null ? suffix.replace("&", "§") : "";
    }

    public String getPlayerWithPrefixAndSuffix(Player p) {
        String prefix = getPrefix(p.getUniqueId());
        String suffix = getSuffix(p.getUniqueId());
        return prefix + (prefix.isEmpty() ? "" : " ") + p.getName() + (suffix.isEmpty() ? "" : " ") + suffix;
    }

}
