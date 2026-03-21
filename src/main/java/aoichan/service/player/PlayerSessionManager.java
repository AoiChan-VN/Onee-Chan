package aoichan.service.player;

import java.util.*;
import aoichan.data.model.PlayerData;

/**
 * 【❅】 Manage PlayerSession lifecycle
 */
public class PlayerSessionManager {

    private final Map<UUID, PlayerSession> sessions = new HashMap<>();

    // 【❅】 Get or create session
    public PlayerSession get(UUID uuid) {
        return sessions.get(uuid);
    }

    public PlayerSession create(UUID uuid, PlayerData data) {
        PlayerSession session = new PlayerSession(data);
        sessions.put(uuid, session);
        return session;
    }

    public PlayerSession getOrCreate(UUID uuid, PlayerData data) {
        return sessions.computeIfAbsent(uuid, u -> new PlayerSession(data));
    }

    public void remove(UUID uuid) {
        sessions.remove(uuid);
    }

    public Collection<PlayerSession> all() {
        return sessions.values();
    }
} 
