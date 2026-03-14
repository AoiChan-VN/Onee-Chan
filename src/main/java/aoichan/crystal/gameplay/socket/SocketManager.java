package aoichan.crystal.gameplay.socket;

import aoichan.crystal.bootstrap.AoiMain;
import aoichan.crystal.gameplay.gem.Gem;
import aoichan.crystal.gameplay.gem.GemManager;
import aoichan.crystal.infrastructure.config.ConfigManager;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class SocketManager {

    public static int getMaxSlots(Material material) {

        // 【!】Code: đọc slot từ config
        String key = material.name();

        return ConfigManager.get().getInt("socket.slots." + key, 0);

    }

    public static SocketData getSocketData(ItemStack item) {

        if (item == null || !item.hasItemMeta()) {
            return new SocketData(0);
        }

        ItemMeta meta = item.getItemMeta();

        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        String data = pdc.get(SocketUtil.SOCKET_KEY, PersistentDataType.STRING);

        int max = getMaxSlots(item.getType());

        SocketData socketData = new SocketData(max);

        if (data == null) {
            return socketData;
        }

        String[] split = data.split(",");

        for (int i = 0; i < split.length; i++) {

            socketData.setGem(i, split[i]);

        }

        return socketData;

    }

    public static void saveSocketData(ItemStack item, SocketData data) {

        if (item == null || !item.hasItemMeta()) return;

        ItemMeta meta = item.getItemMeta();

        PersistentDataContainer pdc = meta.getPersistentDataContainer();

        List<String> gems = data.getGems();

        List<String> out = new ArrayList<>();

        for (String gem : gems) {

            if (gem == null) {
                out.add("empty");
            } else {
                out.add(gem);
            }

        }

        String value = String.join(",", out);

        pdc.set(SocketUtil.SOCKET_KEY, PersistentDataType.STRING, value);

        item.setItemMeta(meta);

    }

    public static boolean addGem(ItemStack item, String gemId) {

        SocketData data = getSocketData(item);

        for (int i = 0; i < data.size(); i++) {

            if (data.getGem(i) == null || data.getGem(i).equals("empty")) {

                data.setGem(i, gemId);

                saveSocketData(item, data);

                return true;

            }

        }

        return false;

    }

    public static List<Gem> getGems(ItemStack item) {

        SocketData data = getSocketData(item);

        List<Gem> list = new ArrayList<>();

        for (String gemId : data.getGems()) {

            if (gemId == null || gemId.equals("empty")) continue;

            Gem gem = GemManager.getGem(gemId);

            if (gem != null) {
                list.add(gem);
            }

        }

        return list;

    }

} 
