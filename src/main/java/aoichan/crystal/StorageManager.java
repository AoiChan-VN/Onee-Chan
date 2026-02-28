package aoidev.crystal;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.bukkit.plugin.Plugin;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.util.*;
import java.util.concurrent.*;
import java.util.logging.Level;

/**
 * Simple JSON storage strategy using an executor for async IO.
 * Production: you can swap to SQLite/Postgres adapter by implementing same public methods.
 */
public class StorageManager {

    private final Plugin plugin;
    private final Gson gson;
    private final ExecutorService ioExecutor;
    private final File storageFolder;

    public StorageManager(Plugin plugin, Gson gson) {
        this.plugin = plugin;
        this.gson = gson;
        this.ioExecutor = Executors.newSingleThreadExecutor(r -> {
            Thread t = new Thread(r, "GemsIO");
            t.setDaemon(true);
            return t;
        });
        this.storageFolder = new File(plugin.getDataFolder(), "data/gems");
        if (!storageFolder.exists()) storageFolder.mkdirs();
    }

    public CompletableFuture<Void> saveGemAsync(Gem gem) {
        return CompletableFuture.runAsync(() -> {
            try {
                File f = fileFor(gem.getId());
                try (Writer w = new FileWriter(f)) {
                    gson.toJson(gem, w);
                }
            } catch (IOException e) {
                plugin.getLogger().log(Level.SEVERE, "Failed to save gem " + gem.getId(), e);
            }
        }, ioExecutor);
    }

    public CompletableFuture<Void> deleteGemAsync(UUID id) {
        return CompletableFuture.runAsync(() -> {
            File f = fileFor(id);
            if (f.exists()) f.delete();
        }, ioExecutor);
    }

    public CompletableFuture<List<Gem>> loadAllAsync() {
        return CompletableFuture.supplyAsync(() -> {
            List<Gem> list = new ArrayList<>();
            File[] files = storageFolder.listFiles((d, name) -> name.endsWith(".json"));
            if (files == null) return list;
            Type gemType = new TypeToken<Gem>(){}.getType();
            for (File f : files) {
                try (Reader r = new FileReader(f)) {
                    Gem g = gson.fromJson(r, gemType);
                    if (g != null) list.add(g);
                } catch (IOException e) {
                    plugin.getLogger().log(Level.WARNING, "Failed reading gem file: " + f.getName(), e);
                }
            }
            // hand over to GemManager on main thread
            plugin.getServer().getScheduler().runTask(plugin, () -> {
                if (plugin instanceof Main) {
                    Main main = (Main) plugin;
                    for (Gem g : list) main.getGemManager().addLoadedGem(g);
                    plugin.getLogger().info("Loaded " + list.size() + " gems.");
                }
            });
            return list;
        }, ioExecutor);
    }

    private File fileFor(UUID id) {
        return new File(storageFolder, id.toString() + ".json");
    }

    public void shutdown() {
        ioExecutor.shutdown();
        try {
            if (!ioExecutor.awaitTermination(3, TimeUnit.SECONDS)) {
                ioExecutor.shutdownNow();
            }
        } catch (InterruptedException e) {
            ioExecutor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
  }
