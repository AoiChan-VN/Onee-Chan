package aoichan.crystal.core.item;

import aoichan.crystal.bootstrap.CrystalPlugin;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.NamespacedKey;

// [!] Code: Gem item utility
public class GemItemUtil {

    public static String getGemId(ItemStack item) {

        if (item == null)
            return null;

        ItemMeta meta =
                item.getItemMeta();

        if (meta == null)
            return null;

        PersistentDataContainer pdc =
                meta.getPersistentDataContainer();

        NamespacedKey key =
                new NamespacedKey(
                        CrystalPlugin.get(),
                        "gem_id"
                );

        return pdc.get(
                key,
                PersistentDataType.STRING
        );
    }

    public static int getGemLevel(ItemStack item) {

        if (item == null)
            return 0;

        ItemMeta meta =
                item.getItemMeta();

        if (meta == null)
            return 0;

        PersistentDataContainer pdc =
                meta.getPersistentDataContainer();

        NamespacedKey key =
                new NamespacedKey(
                        CrystalPlugin.get(),
                        "gem_level"
                );

        Integer level =
                pdc.get(
                        key,
                        PersistentDataType.INTEGER
                );

        return level == null ? 0 : level;
    }

    public static boolean isGem(ItemStack item) {

        return getGemId(item) != null;
    }
} 
