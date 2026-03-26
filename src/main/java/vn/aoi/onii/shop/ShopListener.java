package vn.aoi.onii.shop;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.inventory.InventoryClickEvent;
import vn.aoi.onii.player.PlayerManager;

public class ShopListener implements Listener {

    private final PlayerManager manager;
    private final Economy econ;

    public ShopListener(PlayerManager manager, Economy econ) {
        this.manager = manager;
        this.econ = econ;
    }

    @EventHandler
    public void click(InventoryClickEvent e) {

        if (!(e.getWhoClicked() instanceof Player p)) return;

        if (!e.getView().getTitle().contains("Cửa Hàng")) return;

        e.setCancelled(true);

        var item = e.getCurrentItem();
        if (item == null || !item.hasItemMeta()) return;

        if (item.getItemMeta().getDisplayName().contains("sau")) {
            p.openInventory(new ShopManager().createShop(2));
        }
    }
}
