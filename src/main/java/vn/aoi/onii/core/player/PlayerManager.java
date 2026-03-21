// File: src/main/java/vn/aoi/onii/core/player/PlayerManager.java
package vn.aoi.onii.core.player;

import java.util.HashMap;
import java.util.UUID;

public class PlayerManager {

    private final HashMap<UUID, PlayerData> cache = new HashMap<>();

    public PlayerData get(UUID uuid) {
        return cache.computeIfAbsent(uuid, PlayerData::new);
    }

    public void remove(UUID uuid) {
        cache.remove(uuid);
    }
}
