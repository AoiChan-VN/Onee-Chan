package vn.aoi.onii.manager;

import vn.aoi.onii.data.PlayerData;

import java.util.*;

public class PlayerManager {

    private static final Map<UUID, PlayerData> cache = new HashMap<>();

    public static PlayerData get(UUID uuid) {
        return cache.get(uuid);
    }

    public static void set(PlayerData data) {
        cache.put(data.getUuid(), data);
    }

    public static void remove(UUID uuid) {
        cache.remove(uuid);
    }

    public static Collection<PlayerData> all() {
        return cache.values();
    }
} 
