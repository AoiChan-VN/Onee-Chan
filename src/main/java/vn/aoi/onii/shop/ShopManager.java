package vn.aoi.onii.shop;

import org.bukkit.*;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;
import vn.aoi.onii.Main;

import java.io.File;
import java.util.*;

public class ShopManager {

    private final YamlConfiguration cfg;

    public ShopManager() {
        File file = new File(Main.getInstance().getDataFolder(), "shop.yml");
        cfg = YamlConfiguration.loadConfiguration(file);
    }

    public Inventory createShop(int page) {

        List<String> keys = new ArrayList<>(cfg.getConfigurationSection("shop.items").getKeys(false));

        Inventory inv = Bukkit.createInventory(null, 54, cfg.getString("shop.title") + " [" + page + "]");

        int start = (page - 1) * 28;
        int end = Math.min(start + 28, keys.size());

        int slot = 10;

        for (int i = start; i < end; i++) {

            String path = "shop.items." + keys.get(i);

            Material mat = Material.valueOf(cfg.getString(path + ".material"));
            String name = cfg.getString(path + ".name");

            ItemStack item = new ItemStack(mat);
            ItemMeta meta = item.getItemMeta();

            meta.setDisplayName(name);
            meta.setLore(Arrays.asList("§7Click để mua"));

            item.setItemMeta(meta);
            inv.setItem(slot, item);

            slot++;
            if (slot % 9 == 8) slot += 2;
        }

        inv.setItem(45, arrow("§eTrang trước"));
        inv.setItem(53, arrow("§eTrang sau"));

        return inv;
    }

    private ItemStack arrow(String name) {
        ItemStack i = new ItemStack(Material.ARROW);
        var m = i.getItemMeta();
        m.setDisplayName(name);
        i.setItemMeta(m);
        return i;
    }

    public YamlConfiguration getCfg() {
        return cfg;
    }
}
