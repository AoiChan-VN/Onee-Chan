package vn.aoi.cultivation.listener;

import vn.aoi.cultivation.core.security.AntiDupeManager;
import vn.aoi.cultivation.gui.holder.CustomInventoryHolder;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

public class InventoryCloseListener implements Listener {

    private final AntiDupeManager antiDupeManager;

    public InventoryCloseListener(AntiDupeManager antiDupeManager) {
        this.antiDupeManager = antiDupeManager;
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {

        if (!(event.getPlayer() instanceof Player player)) return;

        Inventory inventory = event.getInventory();
        if (inventory == null) return;

        // CHỈ XỬ LÝ GUI CỦA SYSTEM
        if (!(inventory.getHolder() instanceof CustomInventoryHolder)) {
            return;
        }

        // PHÁT HIỆN HACK / TAMPER STATE
        boolean tampered = antiDupeManager.detectTamper(player, inventory);

        if (tampered) {

            player.sendMessage("§c[System] Phát hiện hành vi bất thường! Rollback dữ liệu...");

            // ROLLBACK INVENTORY VỀ SNAPSHOT
            antiDupeManager.rollback(player, inventory);
        }

        // CLEAN SESSION (memory safety)
        antiDupeManager.clear(player);
    }
} 
