package vn.aoi.onii.manager;

import org.bukkit.entity.Player;
import vn.aoi.onii.data.PlayerData;

import java.util.HashMap;
import java.util.UUID;

public class PlayerManager {

    private static final HashMap<UUID, PlayerData> cache = new HashMap<>();

    public static PlayerData get(Player player) {
        return cache.get(player.getUniqueId());
    }

    public static void addExp(Player player, int amount) {
        PlayerData data = get(player);
        data.addExp(amount);

        LevelManager.checkLevelUp(player, data);
    }

    public static void set(PlayerData data) {
        cache.put(data.getUuid(), data);
    }
}
