package aoichan.crystal.infrastructure.util;

import aoichan.crystal.bootstrap.CrystalPlugin;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

// [!] Code: Item NBT Utility (Gem / Socket / Forge data)
public class ItemNBTUtil {

    private static CrystalPlugin plugin;

    // [!] Code: Initialize util
    public static void init(CrystalPlugin instance) {
        plugin = instance;
    }

    // [!] Code: Create key
    private static NamespacedKey key(String id) {
        return new NamespacedKey(plugin, id);
    }

    // =====================================================
    // STRING
    // =====================================================

    // [!] Code: Set string
    public static void setString(ItemStack item, String id, String value) {

        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;

        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        pdc.set(key(id), PersistentDataType.STRING, value);

        item.setItemMeta(meta);
    }

    // [!] Code: Get string
    public static String getString(ItemStack item, String id) {

        ItemMeta meta = item.getItemMeta();
        if (meta == null) return null;

        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        return pdc.get(key(id), PersistentDataType.STRING);
    }

    // =====================================================
    // INTEGER
    // =====================================================

    // [!] Code: Set int
    public static void setInt(ItemStack item, String id, int value) {

        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;

        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        pdc.set(key(id), PersistentDataType.INTEGER, value);

        item.setItemMeta(meta);
    }

    // [!] Code: Get int
    public static int getInt(ItemStack item, String id) {

        ItemMeta meta = item.getItemMeta();
        if (meta == null) return 0;

        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        Integer value = pdc.get(key(id), PersistentDataType.INTEGER);

        return value == null ? 0 : value;
    }

    // =====================================================
    // BOOLEAN
    // =====================================================

    // [!] Code: Set boolean
    public static void setBoolean(ItemStack item, String id, boolean value) {

        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;

        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        pdc.set(key(id), PersistentDataType.INTEGER, value ? 1 : 0);

        item.setItemMeta(meta);
    }

    // [!] Code: Get boolean
    public static boolean getBoolean(ItemStack item, String id) {

        ItemMeta meta = item.getItemMeta();
        if (meta == null) return false;

        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        Integer value = pdc.get(key(id), PersistentDataType.INTEGER);

        return value != null && value == 1;
    }

    // =====================================================
    // REMOVE
    // =====================================================

    // [!] Code: Remove key
    public static void remove(ItemStack item, String id) {

        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;

        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        pdc.remove(key(id));

        item.setItemMeta(meta);
    }

    // =====================================================
    // CHECK
    // =====================================================

    // [!] Code: Has key
    public static boolean has(ItemStack item, String id) {

        ItemMeta meta = item.getItemMeta();
        if (meta == null) return false;

        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        return pdc.has(key(id), PersistentDataType.STRING)
                || pdc.has(key(id), PersistentDataType.INTEGER);
    }

} 
