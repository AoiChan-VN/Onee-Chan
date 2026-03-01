package aoichan.crystal.core;

import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;

public class AntiDupeManager implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getClick().isShiftClick()) {
            e.setCancelled(true);
        }
    }
}
