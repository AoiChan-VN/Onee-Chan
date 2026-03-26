package vn.aoi.onii.shop;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import vn.aoi.onii.Main;
import vn.aoi.onii.enums.TechniqueTier;
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
    public void onClick(InventoryClickEvent e) {
        ShopManager shopManager = new ShopManager();
        YamlConfiguration config = shopManager.getConfig();

        if (!e.getView().getTitle().equals(config.getString("shop.title"))) return;

        e.setCancelled(true);

        if (!(e.getWhoClicked() instanceof Player player)) return;

        ItemStack item = e.getCurrentItem();
        if (item == null || !item.hasItemMeta()) return;

        for (String key : config.getConfigurationSection("shop.items").getKeys(false)) {

            String path = "shop.items." + key;
            String name = config.getString(path + ".name");

            if (!item.getItemMeta().getDisplayName().equals(name)) continue;

            double price = config.getDouble(path + ".price");
            String tierName = config.getString(path + ".tier");

            if (econ == null) {
                player.sendMessage("§cChưa có Vault!");
                return;
            }

            if (!econ.has(player, price)) {
                player.sendMessage("§cKhông đủ linh thạch!");
                return;
            }

            econ.withdrawPlayer(player, price);

            PlayerData data = manager.get(player.getUniqueId(), player.getName());
            data.setSect("Đã học " + tierName);
            manager.save(data);

            TechniqueTier tier = TechniqueTier.valueOf(tierName);

            player.sendMessage("§aLĩnh ngộ công pháp cấp " + tier.name());
            return;
        }
    }
}
