package aoichan.crystal.core.socket;

import aoichan.crystal.bootstrap.CrystalPlugin;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import org.bukkit.persistence.PersistentDataType;
import org.bukkit.persistence.PersistentDataContainer;

import org.bukkit.NamespacedKey;

// [!] Code: socket storage util
public class SocketItemUtil {

    private static NamespacedKey socketKey =
            new NamespacedKey(
                    CrystalPlugin.get(),
                    "socket_data"
            );

    public static String getSocketData(ItemStack item) {

        if (item == null)
            return null;

        ItemMeta meta =
                item.getItemMeta();

        if (meta == null)
            return null;

        PersistentDataContainer pdc =
                meta.getPersistentDataContainer();

        return pdc.get(
                socketKey,
                PersistentDataType.STRING
        );
    }

    public static void setSocketData(
            ItemStack item,
            String json
    ) {

        ItemMeta meta =
                item.getItemMeta();

        if (meta == null)
            return;

        PersistentDataContainer pdc =
                meta.getPersistentDataContainer();

        pdc.set(
                socketKey,
                PersistentDataType.STRING,
                json
        );

        item.setItemMeta(meta);
    }
} 
