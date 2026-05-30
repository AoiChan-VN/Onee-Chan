package vn.aoi.cultivation.listener;

import vn.aoi.cultivation.core.security.ClickCooldownManager;
import vn.aoi.cultivation.core.security.TransactionGuard;
import vn.aoi.cultivation.gui.holder.CustomInventoryHolder;
import vn.aoi.cultivation.gui.menu.ShopMenu;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryClickListener implements Listener {

    private final ClickCooldownManager clickCooldownManager;
    private final TransactionGuard transactionGuard;
    private final ShopMenu shopMenu;

    public InventoryClickListener(
            ClickCooldownManager clickCooldownManager,
            TransactionGuard transactionGuard,
            ShopMenu shopMenu
    ) {
        this.clickCooldownManager = clickCooldownManager;
        this.transactionGuard = transactionGuard;
        this.shopMenu = shopMenu;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {

        event.setCancelled(true);

        if (!(event.getWhoClicked() instanceof Player player)) {
            return;
        }

        Inventory clickedInventory = event.getClickedInventory();

        if (clickedInventory == null) {
            return;
        }

        ItemStack clickedItem = event.getCurrentItem();

        if (clickedItem == null) {
            return;
        }

        if (!(clickedInventory.getHolder() instanceof CustomInventoryHolder holder)) {
            return;
        }

        if (!clickCooldownManager.allowClick(player.getUniqueId())) {
            return;
        }

        String guiId = holder.getGuiId();

        switch (guiId) {

            case "main_menu":
                handleMainMenuClick(
                        player,
                        clickedItem
                );
                break;

            case ShopMenu.GUI_ID:
                handleShopMenuClick(
                        player,
                        clickedItem
                );
                break;

            default:
                break;
        }
    }

    private void handleMainMenuClick(
            Player player,
            ItemStack item
    ) {

        Material material = item.getType();

        if (material == Material.EMERALD) {

            shopMenu.open(player);
        }
    }

    private void handleShopMenuClick(
            Player player,
            ItemStack item
    ) {

        Material material = item.getType();

        if (material == Material.BARRIER) {

            player.closeInventory();
            return;
        }

        if (material == Material.EMERALD) {

            transactionGuard.runAtomic(
                    player,
                    () -> executePurchase(
                            player,
                            Material.EMERALD,
                            1
                    )
            );

            return;
        }

        if (material == Material.DIAMOND) {

            transactionGuard.runAtomic(
                    player,
                    () -> executePurchase(
                            player,
                            Material.DIAMOND,
                            1
                    )
            );

            return;
        }

        if (material == Material.NETHER_STAR) {

            transactionGuard.runAtomic(
                    player,
                    () -> executePurchase(
                            player,
                            Material.NETHER_STAR,
                            1
                    )
            );
        }
    }

    private void executePurchase(
            Player player,
            Material material,
            int amount
    ) {

        ItemStack reward =
                new ItemStack(
                        material,
                        amount
                );

        player.getInventory().addItem(reward);
    }
}
