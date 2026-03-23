package vn.aoi.onii.skill;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class CooldownManager {

    private final Map<UUID, Map<String, Long>> cooldowns = new ConcurrentHashMap<>();

    public boolean isOnCooldown(UUID uuid, String skill) {
        Map<String, Long> map = cooldowns.get(uuid);
        if (map == null) return false;

        Long time = map.get(skill);
        return time != null && System.currentTimeMillis() < time;
    }

    public void setCooldown(UUID uuid, String skill, long delay) {
        cooldowns.computeIfAbsent(uuid, k -> new ConcurrentHashMap<>())
                .put(skill, System.currentTimeMillis() + delay);
    }
}
