package aoidev.crystal;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Lightweight anti-dupe basic system.
 * Tracks recent transaction UUIDs and timestamps to avoid duplicates.
 */
public class AntiDuper {

    private final long windowMs;
    private final Map<String, Long> recent = new ConcurrentHashMap<>();

    public AntiDuper(long windowMs) {
        this.windowMs = Math.max(500, windowMs);
    }

    /**
     * Returns true if transaction is allowed (not duplicate within window).
     */
    public boolean checkAndMark(String txKey) {
        long now = System.currentTimeMillis();
        Long last = recent.get(txKey);
        if (last != null && (now - last) <= windowMs) {
            return false;
        }
        recent.put(txKey, now);
        // lazy cleanup
        recent.entrySet().removeIf(e -> (now - e.getValue()) > windowMs * 10);
        return true;
    }
}
