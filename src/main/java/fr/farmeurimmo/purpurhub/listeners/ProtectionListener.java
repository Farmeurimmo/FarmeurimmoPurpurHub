package fr.farmeurimmo.purpurhub.listeners;

import fr.farmeurimmo.purpurhub.PurpurHub;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerAttemptPickupItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.purpurmc.purpur.event.entity.PreEntityExplodeEvent;

public class ProtectionListener implements Listener {

    @EventHandler
    public void blockBreak(BlockBreakEvent e) {
        if (PurpurHub.INSTANCE.build.contains(e.getPlayer().getUniqueId())) return;

        e.setCancelled(true);
    }

    @EventHandler
    public void blockPlace(BlockPlaceEvent e) {
        if (PurpurHub.INSTANCE.build.contains(e.getPlayer().getUniqueId())) return;

        e.setCancelled(true);
    }

    @EventHandler
    public void blockExplode(BlockExplodeEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void explosionPrime(ExplosionPrimeEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void blockBurn(BlockBurnEvent e) {
        e.setCancelled(true);
        if (e.getIgnitingBlock() == null) return;
        e.getIgnitingBlock().setType(e.getBlock().getType());
    }

    @EventHandler
    public void blockDropItem(BlockDropItemEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void playerDropItem(PlayerDropItemEvent e) {
        if (PurpurHub.INSTANCE.build.contains(e.getPlayer().getUniqueId())) return;

        e.setCancelled(true);
    }

    @EventHandler
    public void playerPickupItem(PlayerAttemptPickupItemEvent e) {
        if (PurpurHub.INSTANCE.build.contains(e.getPlayer().getUniqueId())) return;

        e.setCancelled(true);
    }

    @EventHandler
    public void entitySpawn(EntitySpawnEvent e) {
        if (e.getEntity() instanceof Player) return;

        e.setCancelled(true);
    }

    @EventHandler
    public void preEntityExplode(PreEntityExplodeEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void entityExplode(EntityExplodeEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void inventoryClick(InventoryClickEvent e) {
        if (PurpurHub.INSTANCE.build.contains(e.getWhoClicked().getUniqueId())) return;

        e.setCancelled(true);
    }

    @EventHandler
    public void dragClick(InventoryDragEvent e) {
        if (PurpurHub.INSTANCE.build.contains(e.getWhoClicked().getUniqueId())) return;

        e.setResult(Event.Result.DENY);
    }
}
