package vn.aoi.onii.manager;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import vn.aoi.onii.database.PlayerRepository;
import vn.aoi.onii.model.Cultivator;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class PlayerManager {

    private final PlayerRepository repository;

    private final Cache<UUID, Cultivator> cache;

    private final ConcurrentHashMap<UUID, Boolean> onlinePlayers = new ConcurrentHashMap<>();

    public PlayerManager(PlayerRepository repository) {
        this.repository = repository;

        this.cache = Caffeine.newBuilder()
                .expireAfterAccess(10, TimeUnit.MINUTES)
                .maximumSize(10_000)
                .build();
    }

    public void loadPlayer(UUID uuid) {

        onlinePlayers.put(uuid, true);

        repository.load(uuid).thenAccept(cultivator -> {

            if (cultivator == null) {
                cultivator = Cultivator.builder()
                        .uuid(uuid)
                        .realm("Phàm nhân")
                        .level(1)
                        .exp(0)
                        .build();
            }

            cache.put(uuid, cultivator);
        });
    }

    public void savePlayer(UUID uuid) {
        Cultivator cultivator = cache.getIfPresent(uuid);
        if (cultivator != null) {
            repository.save(cultivator);
        }
    }

    public void unloadPlayer(UUID uuid) {
        savePlayer(uuid);
        cache.invalidate(uuid);
        onlinePlayers.remove(uuid);
    }

    public Cultivator get(UUID uuid) {
        return cache.getIfPresent(uuid);
    }

    public ConcurrentHashMap<UUID, Boolean> getOnlinePlayers() {
        return onlinePlayers;
    }
}
