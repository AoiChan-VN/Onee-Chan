package aoichan.crystal.core.cache;

import aoichan.crystal.api.GemData;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

// [!] Code: Gem Cache (Lazy Loading Storage)
public class GemCache {

    private final ConcurrentHashMap<UUID, GemData> cache = new ConcurrentHashMap<>();

    public GemData get(UUID uuid) {
        return cache.get(uuid);
    }

    public void put(UUID uuid, GemData data) {
        cache.put(uuid, data);
    }

    public boolean contains(UUID uuid) {
        return cache.containsKey(uuid);
    }

    public void remove(UUID uuid) {
        cache.remove(uuid);
    }

    public ConcurrentHashMap<UUID, GemData> getAll() {
        return cache;
    }
} 
