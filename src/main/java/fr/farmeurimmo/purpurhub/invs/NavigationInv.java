package fr.farmeurimmo.purpurhub.invs;

import fr.farmeurimmo.purpurhub.managers.ItemManager;
import fr.farmeurimmo.purpurhub.managers.ServerConnector;
import fr.mrmicky.fastinv.FastInv;
import fr.mrmicky.fastinv.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class NavigationInv extends FastInv {

    public NavigationInv() {
        super(27, "§6Navigation");

        setItem(18, ItemBuilder.copyOf(new ItemStack(Material.RED_BED)).name("§eBack to the spawn").build(), e -> {
            e.getWhoClicked().closeInventory();
            e.getWhoClicked().teleport(e.getWhoClicked().getWorld().getSpawnLocation());
        });

        setItem(0, ItemBuilder.copyOf(new ItemStack(Material.RECOVERY_COMPASS)).name("§eDemo server 01").build(),
                e -> ServerConnector.INSTANCE.connect((Player) e.getWhoClicked(), "demo01"));

        setItem(12, ItemManager.INSTANCE.getRPGItem(), e -> e.getWhoClicked().sendMessage("§cComing soon..."));

        setItem(14, ItemManager.INSTANCE.getSOONItem());
    }
}
