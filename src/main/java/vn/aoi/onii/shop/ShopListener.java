package vn.aoi.onii.shop;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import vn.aoi.onii.player.*;

public class ShopListener implements Listener {

    private final PlayerManager manager;
    private final Economy econ;

    public ShopListener(PlayerManager manager, Economy econ) {
        this.manager = manager;
        this.econ = econ;
    }

    @EventHandler
    public void click(InventoryClickEvent e) {

        if (!(e.getWhoClicked() instanceof Player player)) return;

        String title = e.getView().getTitle();
        if (!title.contains("Cửa Hàng")) return;

        e.setCancelled(true);

        ShopManager shop = new ShopManager();
        YamlConfiguration config = shop.getConfig();

        ItemStack item = e.getCurrentItem();
        if (item == null || !item.hasItemMeta()) return;

        if (item.getItemMeta().getDisplayName().equals("§e➡")) {
            player.openInventory(shop.createShop(getPage(title) + 1));
            return;
        }

        if (item.getItemMeta().getDisplayName().equals("§e⬅")) {
            player.openInventory(shop.createShop(Math.max(1, getPage(title) - 1)));
            return;
        }

        for (String key : config.getConfigurationSection("shop.items").getKeys(false)) {

            String path = "shop.items." + key;

            if (!item.getItemMeta().getDisplayName().equals(config.getString(path + ".name"))) continue;

            double price = config.getDouble(path + ".price");
            String tier = config.getString(path + ".tier");

            PlayerData data = manager.get(player.getUniqueId(), player.getName());

            if (data.getTechnique().equalsIgnoreCase(tier)) {
                player.sendMessage("§eĐã học rồi!");
                return;
            }

            if (!econ.has(player, price)) {
                player.sendMessage("§cKhông đủ tiền!");
                return;
            }

            econ.withdrawPlayer(player, price);

            data.setTechnique(tier);
            manager.save(data);

            player.sendMessage("§aĐã học " + tier);
            return;
        }
    }

    private int getPage(String title) {
        try {
            return Integer.parseInt(title.split("\\[")[1].replace("]", ""));
        } catch (Exception e) {
            return 1;
        }
    }
}
