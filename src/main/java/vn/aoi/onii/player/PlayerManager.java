package vn.aoi.onii.player;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.player.*;

import vn.aoi.onii.Main;
import vn.aoi.onii.database.DatabaseManager;
import vn.aoi.onii.scheduler.AsyncExecutor;
import vn.aoi.onii.player.repository.impl.SQLitePlayerRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerManager implements Listener {

    private final Main plugin;
    private final AsyncExecutor executor;
    private final SQLitePlayerRepository repo;

    private final Map<UUID, PlayerData> cache = new ConcurrentHashMap<>();

    public PlayerManager(Main plugin, DatabaseManager db, AsyncExecutor executor) {
        this.plugin = plugin;
        this.executor = executor;
        this.repo = new SQLitePlayerRepository(db);
    }

    public void init() {
        Bukkit.getPluginManager().registerEvents(this, plugin);

        Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, () -> {
            for (PlayerData d : cache.values()) {
                if (d.isDirty()) executor.run(() -> repo.save(d));
            }
        }, 200, 200);
    }

    @EventHandler
    public void join(PlayerJoinEvent e) {
        executor.run(() -> cache.put(e.getPlayer().getUniqueId(), repo.load(e.getPlayer().getUniqueId())));
    }

    @EventHandler
    public void quit(PlayerQuitEvent e) {
        PlayerData d = cache.remove(e.getPlayer().getUniqueId());
        if (d != null && d.isDirty()) executor.run(() -> repo.save(d));
    }

    public PlayerData get(Player p) {
        return cache.get(p.getUniqueId());
    }

    public Collection<PlayerData> getOnline() {
        return cache.values();
    }

    public void shutdown() {
        for (PlayerData d : cache.values()) {
            if (d.isDirty()) repo.save(d);
        }
    }
}
