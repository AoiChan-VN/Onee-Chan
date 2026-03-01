package aoichan.crystal.core;

import aoichan.crystal.utils.PDCUtil;
import org.bukkit.event.*;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.ItemStack;

public class AntiDupeManager implements Listener {

    private final SocketManager socketManager;

    public AntiDupeManager(SocketManager socketManager) {
        this.socketManager = socketManager;
    }

    @EventHandler(ignoreCancelled = true)
    public void onClick(InventoryClickEvent e) {

        if (e.getCurrentItem() == null) return;

        ItemStack item = e.getCurrentItem();

        if (PDCUtil.hasGemTag(item)) {
            if (e.getClick().isShiftClick() ||
                e.getAction() == InventoryAction.COLLECT_TO_CURSOR) {

                e.setCancelled(true);
            }
        }
    }
}
