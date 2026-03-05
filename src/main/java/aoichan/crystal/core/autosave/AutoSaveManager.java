package aoichan.crystal.core.autosave;

import aoichan.crystal.AoiMain;
import aoichan.crystal.core.cache.DirtyTracker;
import aoichan.crystal.storage.StorageManager;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import java.util.UUID;

// [!] Code: Global Debounce Auto Save System
public class AutoSaveManager {

    private final AoiMain plugin;
    private final DirtyTracker dirtyTracker;
    private final StorageManager storageManager;

    private BukkitTask autoSaveTask;

    public AutoSaveManager(AoiMain plugin,
                           DirtyTracker dirtyTracker,
                           StorageManager storageManager) {
        this.plugin = plugin;
        this.dirtyTracker = dirtyTracker;
        this.storageManager = storageManager;
    }

    public void scheduleSave() {

        if (autoSaveTask != null) return;

        int delay = plugin.getConfig().getInt("auto-save.delay-seconds", 5);

        if (plugin.getConfig().getBoolean("auto-save.console-log", true)) {
            Bukkit.getConsoleSender().sendMessage("[Aoi Save] Saving data in " + delay + " seconds...");
        }

        autoSaveTask = Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, () -> {

            if (dirtyTracker.isEmpty()) {
                autoSaveTask = null;
                return;
            }

            storageManager.saveBatch(dirtyTracker.getDirtyPlayers());

            if (plugin.getConfig().getBoolean("auto-save.console-log", true)) {
                Bukkit.getConsoleSender().sendMessage("[Aoi Save] Data saved successfully.");
            }

            dirtyTracker.clear();
            autoSaveTask = null;

        }, delay * 20L);
    }

    public void shutdownSaveSync() {
        if (autoSaveTask != null) {
            autoSaveTask.cancel();
        }

        if (!dirtyTracker.isEmpty()) {
            storageManager.saveBatchSync(dirtyTracker.getDirtyPlayers());
            dirtyTracker.clear();
        }
    }
} 
