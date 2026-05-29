package vn.aoi.cultivation.listener;

import vn.aoi.cultivation.core.security.ClickCooldownManager;
import vn.aoi.cultivation.core.security.TransactionGuard;
import vn.aoi.cultivation.gui.holder.CustomInventoryHolder;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryClickListener implements Listener {

    private final ClickCooldownManager clickCooldownManager;
    private final TransactionGuard transactionGuard;

    public InventoryClickListener(ClickCooldownManager clickCooldownManager,
                                  TransactionGuard transactionGuard) {
        this.clickCooldownManager = clickCooldownManager;
        this.transactionGuard = transactionGuard;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {

        // 🧱 FIRST LINE DEFENSE - NULL SAFETY
        event.setCancelled(true);

        if (event.getWhoClicked() == null) return;
        if (!(event.getWhoClicked() instanceof Player player)) return;

        Inventory clickedInventory = event.getClickedInventory();
        if (clickedInventory == null) return;

        ItemStack item = event.getCurrentItem();
        if (item == null) return;

        // 🧬 HOLDER VALIDATION (ANTI TITLE SPOOF)
        if (!(clickedInventory.getHolder() instanceof CustomInventoryHolder holder)) {
            return;
        }

        // 🕒 CLICK COOLDOWN CHECK (ANTI AUTOCLICK / MACRO)
        if (!clickCooldownManager.allowClick(player.getUniqueId())) {
            player.sendMessage("§c[System] Bạn click quá nhanh!");
            return;
        }

        // ⚔️ TRANSACTION SAFETY LAYER (ANTI RACE CONDITION)
        transactionGuard.runAtomic(player, () -> {

            String guiId = holder.getGuiId();

            // 🔀 ROUTING LOGIC (placeholder for menu system)
            switch (guiId) {

                case "main_menu" -> {
                    player.sendMessage("§a[Main Menu] Click processed safely.");
                }

                case "shop_menu" -> {
                    player.sendMessage("§e[Shop] Transaction ready.");
                }

                default -> {
                    player.sendMessage("§7[System] Unknown GUI.");
                }
            }
        });
    }
} 
