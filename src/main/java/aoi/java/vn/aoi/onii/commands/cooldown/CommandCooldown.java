package vn.aoi.onii.commands.cooldown;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class CommandCooldown {

    private static final Map<String, Map<UUID, Long>> cooldowns = new ConcurrentHashMap<>();

    public static boolean isOnCooldown(String command, UUID uuid, long cooldownMs) {

        cooldowns.putIfAbsent(command, new ConcurrentHashMap<>());

        long now = System.currentTimeMillis();
        long last = cooldowns.get(command).getOrDefault(uuid, 0L);

        if (now - last < cooldownMs) {
            return true;
        }

        cooldowns.get(command).put(uuid, now);
        return false;
    }

    public static long getRemaining(String command, UUID uuid, long cooldownMs) {
        long last = cooldowns.getOrDefault(command, new ConcurrentHashMap<>())
                .getOrDefault(uuid, 0L);

        return Math.max(cooldownMs - (System.currentTimeMillis() - last), 0);
    }
} 
