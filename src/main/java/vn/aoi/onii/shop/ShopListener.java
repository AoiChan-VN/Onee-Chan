package vn.aoi.onii.shop;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import vn.aoi.onii.Main;
import vn.aoi.onii.player.PlayerData;
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

        if (!(e.getWhoClicked() instanceof Player player)) return;

        String title = e.getView().getTitle();
        if (!title.contains("Cửa Hàng")) return;

        e.setCancelled(true);

        ShopManager shop = new ShopManager();
        YamlConfiguration config = shop.getConfig();

        ItemStack item = e.getCurrentItem();
        if (item == null || !item.hasItemMeta()) return;

        // next page
        if (item.getItemMeta().getDisplayName().contains("Trang sau")) {
            int page = getPage(title) + 1;
            player.openInventory(shop.createShop(page));
            return;
        }

        // prev page
        if (item.getItemMeta().getDisplayName().contains("Trang trước")) {
            int page = Math.max(1, getPage(title) - 1);
            player.openInventory(shop.createShop(page));
            return;
        }

        for (String key : config.getConfigurationSection("shop.items").getKeys(false)) {

            String path = "shop.items." + key;

            if (!item.getItemMeta().getDisplayName().equals(config.getString(path + ".name"))) continue;

            double price = config.getDouble(path + ".price");
            String tier = config.getString(path + ".tier");

            PlayerData data = manager.get(player.getUniqueId(), player.getName());

            // đã học rồi
            if (data.getTechnique().equalsIgnoreCase(tier)) {
                player.sendMessage("§eBạn đã học công pháp này rồi!");
                return;
            }

            if (!econ.has(player, price)) {
                player.sendMessage("§cKhông đủ linh thạch!");
                return;
            }

            econ.withdrawPlayer(player, price);

            data.setTechnique(tier);
            manager.save(data);

            player.sendMessage("§aBạn đã lĩnh ngộ công pháp §e" + tier);
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
