package vn.aoi.onii.shop;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;
import vn.aoi.onii.Main;
import vn.aoi.onii.enums.TechniqueTier;

import java.util.*;

public class ShopManager {

    private final FileConfiguration config;

    public ShopManager() {
        this.config = Main.getInstance().getConfig(); // sẽ load riêng file shop.yml bên dưới
    }

    public Inventory createShop() {
        FileConfiguration shop = Main.getInstance().getConfig();

        String title = shop.getString("shop.title", "Shop");
        int size = 27;

        Inventory inv = Bukkit.createInventory(null, size, title);

        for (String key : shop.getConfigurationSection("shop.items").getKeys(false)) {

            String path = "shop.items." + key;

            Material mat = Material.valueOf(shop.getString(path + ".material", "BOOK"));
            String name = shop.getString(path + ".name");
            double price = shop.getDouble(path + ".price");
            String tier = shop.getString(path + ".tier");

            ItemStack item = new ItemStack(mat);
            ItemMeta meta = item.getItemMeta();

            meta.setDisplayName(name);

            List<String> lore = new ArrayList<>();
            lore.add("§7Cấp: §e" + tier);
            lore.add("§7Giá: §a" + price);
            lore.add("§8Click để mua");

            meta.setLore(lore);
            item.setItemMeta(meta);

            inv.addItem(item);
        }

        return inv;
    }
}
