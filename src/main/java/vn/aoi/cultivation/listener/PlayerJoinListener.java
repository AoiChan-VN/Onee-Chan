package vn.aoi.cultivation.listener;

import vn.aoi.cultivation.core.security.ClickCooldownManager;
import vn.aoi.cultivation.core.security.AntiDupeManager;
import vn.aoi.cultivation.core.security.TransactionGuard;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class PlayerJoinListener implements Listener {

    private final JavaPlugin plugin;
    private final ClickCooldownManager clickCooldownManager;
    private final AntiDupeManager antiDupeManager;
    private final TransactionGuard transactionGuard;

    public PlayerJoinListener(JavaPlugin plugin,
                              ClickCooldownManager clickCooldownManager,
                              AntiDupeManager antiDupeManager,
                              TransactionGuard transactionGuard) {
        this.plugin = plugin;
        this.clickCooldownManager = clickCooldownManager;
        this.antiDupeManager = antiDupeManager;
        this.transactionGuard = transactionGuard;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();

        // ASYNC PRELOAD (không block main thread)
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {

            // Placeholder preload logic (DB / file / cache future expansion)
            player.sendMessage("§7[System] Loading Cultivation data...");

            try {
                Thread.sleep(50); // simulate lightweight preload
            } catch (InterruptedException ignored) {}

        });

        // 🧷 RESET RUNTIME STATE (MAIN THREAD SAFE)

        clickCooldownManager.remove(player.getUniqueId());
        antiDupeManager.clear(player);
        transactionGuard.forceUnlock(player);

        player.sendMessage("§a[System] Cultivation system initialized.");
    }
} 
