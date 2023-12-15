package fr.farmeurimmo.purpurhub.managers;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class ItemManager {

    public static ItemManager INSTANCE;

    public ItemManager() {
        INSTANCE = this;
    }

    public ItemStack getRPGItem() {
        ItemStack item = new ItemStack(Material.DIAMOND_SWORD);
        item.setDisplayName("§6Rpg");
        ArrayList<Component> lore = new ArrayList<>();
        lore.add(Component.text("§cIn development..."));
        item.lore(lore);
        return item;
    }

    public ItemStack getNavigationItem() {
        ItemStack item = new ItemStack(Material.NETHER_STAR);
        item.setDisplayName("§6Navigation");
        ArrayList<Component> lore = new ArrayList<>();
        lore.add(Component.text("§7Click to open the navigation menu"));
        item.lore(lore);
        return item;
    }

    public ItemStack getSOONItem() {
        ItemStack item = new ItemStack(Material.BEDROCK);
        item.setDisplayName("§c");
        return item;
    }
}
