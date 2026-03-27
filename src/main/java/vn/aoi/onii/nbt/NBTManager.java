package vn.aoi.onii.nbt;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.*;
import vn.aoi.onii.Main;

public class NBTManager {

    public static void set(ItemStack item, String key, String value) {
        ItemMeta meta = item.getItemMeta();

        meta.getPersistentDataContainer().set(
                new NamespacedKey(Main.getInstance(), key),
                PersistentDataType.STRING,
                value
        );

        item.setItemMeta(meta);
    }

    public static String get(ItemStack item, String key) {
        if (item == null || !item.hasItemMeta()) return null;

        return item.getItemMeta().getPersistentDataContainer().get(
                new NamespacedKey(Main.getInstance(), key),
                PersistentDataType.STRING
        );
    }
} 
