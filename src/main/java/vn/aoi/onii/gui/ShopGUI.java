package vn.aoi.onii.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.*;

public class ShopGUI {

    public static void open(Player p, FileConfiguration config) {

        Inventory inv = Bukkit.createInventory(null, 27, config.getString("title"));

        config.getConfigurationSection("items").getKeys(false).forEach(key -> {
            String path = "items." + key;

            Material mat = Material.valueOf(config.getString(path + ".material"));
            String name = config.getString(path + ".name");
            int slot = config.getInt(path + ".slot");

            ItemStack item = new ItemStack(mat);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(name);
            item.setItemMeta(meta);

            inv.setItem(slot, item);
        });

        p.openInventory(inv);
    }
}
