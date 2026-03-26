package vn.aoi.onii.shop;

import org.bukkit.*;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;
import vn.aoi.onii.Main;

import java.io.File;
import java.util.*;

public class ShopManager {

    private final YamlConfiguration config;

    public ShopManager() {
        config = YamlConfiguration.loadConfiguration(Main.getInstance().getShopFile());
    }

    public Inventory createShop(int page) {

        List<String> keys = new ArrayList<>(config.getConfigurationSection("shop.items").getKeys(false));

        int size = 54;
        Inventory inv = Bukkit.createInventory(null, size, config.getString("shop.title") + " §7[" + page + "]");

        int start = (page - 1) * 28;
        int end = Math.min(start + 28, keys.size());

        int slot = 10;

        for (int i = start; i < end; i++) {

            String key = keys.get(i);
            String path = "shop.items." + key;

            Material mat = Material.valueOf(config.getString(path + ".material"));
            String name = config.getString(path + ".name");
            double price = config.getDouble(path + ".price");
            String tier = config.getString(path + ".tier");

            ItemStack item = new ItemStack(mat);
            ItemMeta meta = item.getItemMeta();

            meta.setDisplayName(name);
            meta.setLore(Arrays.asList(
                    "§7Cấp: §e" + tier,
                    "§7Giá: §a" + price,
                    "§8Click để lĩnh ngộ"
            ));

            item.setItemMeta(meta);
            inv.setItem(slot, item);

            slot++;
            if (slot % 9 == 8) slot += 2;
        }

        // nút điều hướng
        inv.setItem(45, createButton("§e⬅ Trang trước"));
        inv.setItem(53, createButton("§eTrang sau ➡"));

        return inv;
    }

    private ItemStack createButton(String name) {
        ItemStack item = new ItemStack(Material.ARROW);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        item.setItemMeta(meta);
        return item;
    }

    public YamlConfiguration getConfig() {
        return config;
    }
}
