package aoidev.crystal;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.*;
import java.util.concurrent.CompletableFuture;

/**
 * Thread-safe manager for in-memory gem instances.
 */
public class GemManager {

    private final Plugin plugin;
    private final StorageManager storage;
    // Keep map of id -> Gem
    private final Map<UUID, Gem> gems = Collections.synchronizedMap(new LinkedHashMap<>());

    public GemManager(Plugin plugin, StorageManager storage) {
        this.plugin = plugin;
        this.storage = storage;
    }

    public Optional<Gem> getGem(UUID id) {
        return Optional.ofNullable(gems.get(id));
    }

    public List<Gem> getAllGems() {
        synchronized (gems) {
            return new ArrayList<>(gems.values());
        }
    }

    public CompletableFuture<Void> createAndSaveGemAsync(String type, int level) {
        UUID id = UUID.randomUUID();
        Gem gem = new Gem(id, type, level);
        gems.put(id, gem);
        return storage.saveGemAsync(gem);
    }

    public void addLoadedGem(Gem gem) {
        gems.put(gem.getId(), gem);
    }

    public CompletableFuture<Void> removeGemAsync(UUID id) {
        gems.remove(id);
        return storage.deleteGemAsync(id);
    }
}
