package aoidev.crystal;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

/**
 * Simple inventory-based UI system for viewing gems.
 * Real production: add pagination, async loads, and client-side optimizations.
 */
public class UISystem {

    private final Main plugin;
    private final GemManager gemManager;

    public UISystem(Main plugin, GemManager gemManager) {
        this.plugin = plugin;
        this.gemManager = gemManager;
    }

    public void openGemOverview(Player player) {
        int size = plugin.getConfig().getInt("ui.inventory-size", 54);
        Inventory inv = Bukkit.createInventory(null, size, "Gems Ultimate");
        // TODO: build items for gems (using ItemMeta and custom model data)
        player.openInventory(inv);
    }

    public void handleClick(InventoryClickEvent event) {
        // placeholder handler
        event.setCancelled(true);
    }
}
