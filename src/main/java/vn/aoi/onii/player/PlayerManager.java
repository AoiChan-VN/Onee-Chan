package vn.aoi.onii.player;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerManager {

    private final ConcurrentHashMap<UUID, PlayerData> dataMap = new ConcurrentHashMap<>();

    public PlayerData get(UUID uuid) {
        return dataMap.computeIfAbsent(uuid, k -> new PlayerData());
    }
} 
