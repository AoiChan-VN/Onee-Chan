package vn.aoi.cultivation.core.security;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ClickCooldownManager {

    // UUID -> last click timestamp (ms)
    private final ConcurrentHashMap<UUID, Long> clickMap = new ConcurrentHashMap<>();

    // 200ms anti spam threshold
    private static final long COOLDOWN_MS = 200L;

    /**
     * Kiểm tra người chơi có được phép click hay không
     * @param uuid Player UUID
     * @return true = allowed, false = blocked
     */
    public boolean allowClick(UUID uuid) {
        long now = System.currentTimeMillis();

        Long last = clickMap.get(uuid);

        if (last == null) {
            clickMap.put(uuid, now);
            return true;
        }

        long diff = now - last;

        if (diff < COOLDOWN_MS) {
            return false; // blocked due to spam
        }

        clickMap.put(uuid, now);
        return true;
    }

    /**
     * Xoá cache khi player thoát server để tránh memory leak
     */
    public void remove(UUID uuid) {
        clickMap.remove(uuid);
    }

    /**
     * Dọn toàn bộ cache (shutdown safety)
     */
    public void clearAll() {
        clickMap.clear();
    }

    /**
     * Debug size map (giúp theo dõi leak)
     */
    public int size() {
        return clickMap.size();
    }
} 
