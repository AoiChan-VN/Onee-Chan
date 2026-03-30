package vn.aoi.onii.item;

import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.NamespacedKey;
import vn.aoi.onii.Main;

public class ItemCore {

    private static NamespacedKey qualityKey = new NamespacedKey(Main.get(), "quality");

    public static void applyQuality(ItemStack item, Quality quality) {
        var meta = item.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();
        container.set(qualityKey, PersistentDataType.STRING, quality.name());
        item.setItemMeta(meta);
    }

    public static Quality getQuality(ItemStack item) {
        var meta = item.getItemMeta();
        if (meta == null) return Quality.PHAM;

        var container = meta.getPersistentDataContainer();
        String q = container.get(qualityKey, PersistentDataType.STRING);

        if (q == null) return Quality.PHAM;
        return Quality.valueOf(q);
    }
} 
