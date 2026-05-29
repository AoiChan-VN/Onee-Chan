package vn.aoi.cultivation.core.security;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class TransactionGuard {

    private final JavaPlugin plugin;

    /**
     * Lock giao dịch theo player để chống race-condition
     */
    private final Map<UUID, Boolean> transactionLock = new ConcurrentHashMap<>();

    /**
     * Cooldown chống spam transaction (ms)
     */
    private final Map<UUID, Long> lastTransaction = new ConcurrentHashMap<>();

    private static final long TRANSACTION_COOLDOWN = 250L;

    public TransactionGuard(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Kiểm tra player có thể bắt đầu transaction không
     */
    public boolean canStart(Player player) {
        UUID uuid = player.getUniqueId();
        long now = System.currentTimeMillis();

        if (transactionLock.getOrDefault(uuid, false)) {
            return false; // đang bị khóa transaction
        }

        long last = lastTransaction.getOrDefault(uuid, 0L);

        if (now - last < TRANSACTION_COOLDOWN) {
            return false; // spam transaction
        }

        return true;
    }

    /**
     * Bắt đầu transaction atomic
     */
    public boolean begin(Player player) {
        UUID uuid = player.getUniqueId();

        if (!canStart(player)) {
            return false;
        }

        transactionLock.put(uuid, true);
        return true;
    }

    /**
     * Kết thúc transaction (release lock)
     */
    public void end(Player player) {
        UUID uuid = player.getUniqueId();

        transactionLock.remove(uuid);
        lastTransaction.put(uuid, System.currentTimeMillis());
    }

    /**
     * Thực thi transaction an toàn trong main thread
     */
    public void runAtomic(Player player, Runnable action) {
        if (!begin(player)) {
            player.sendMessage("§c[System] Transaction blocked (cooldown or locked).");
            return;
        }

        Bukkit.getScheduler().runTask(plugin, () -> {
            try {
                action.run();
            } finally {
                end(player);
            }
        });
    }

    /**
     * Force unlock (emergency cleanup)
     */
    public void forceUnlock(Player player) {
        UUID uuid = player.getUniqueId();
        transactionLock.remove(uuid);
    }

    /**
     * Shutdown cleanup
     */
    public void clearAll() {
        transactionLock.clear();
        lastTransaction.clear();
    }
} 
