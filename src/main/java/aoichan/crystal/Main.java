package aoidev.crystal;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public final class Main extends JavaPlugin {

    private static Main instance;
    private Gson gson;
    private GemManager gemManager;
    private StorageManager storageManager;
    private SocketServer socketServer;
    private UISystem uiSystem;
    private AdminCommands adminCommands;
    private AntiDuper antiDuper;

    public static Main getInstance() {
        return instance;
    }

    @Override
    public void onLoad() {
        instance = this;
        saveDefaultConfig();
    }

    @Override
    public void onEnable() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();

        // Initialize core subsystems
        this.storageManager = new StorageManager(this, gson);
        this.gemManager = new GemManager(this, storageManager);
        this.uiSystem = new UISystem(this, gemManager);
        this.antiDuper = new AntiDuper(getConfig().getLong("anti_dupe.recent_window_ms", 2000));
        this.adminCommands = new AdminCommands(this, gemManager, storageManager);

        // Socket server (optional)
        if (getConfig().getBoolean("socket.enabled", true)) {
            int port = getConfig().getInt("socket.port", 31337);
            this.socketServer = new SocketServer(this, port, gemManager);
            socketServer.start();
        }

        // Register command(s)
        getCommand("gems").setExecutor(adminCommands);
        getCommand("gems").setTabCompleter(adminCommands);

        // Register listeners
        getServer().getPluginManager().registerEvents(new CoreListeners(this, gemManager, antiDuper), this);

        // Load existing gems async
        storageManager.loadAllAsync().exceptionally(ex -> {
            getLogger().log(Level.SEVERE, "Failed loading gems", ex);
            return null;
        });

        printConsoleBanner();
        getLogger().info("Gems Ultimate enabled. Ready for action!");
    }

    @Override
    public void onDisable() {
        if (socketServer != null) socketServer.shutdown();
        storageManager.shutdown();
        getLogger().info("Gems Ultimate disabled.");
    }

    private void printConsoleBanner() {
        String banner =
                "\n\n" +
                "  ____                     _                 _   _ _ _       _   _ _ _       \n" +
                " / ___|_ __ __ _ _ __ ___ | |__   ___   ___ | |_(_) (_) ___ | |_(_) |_ ___ \n" +
                "| |  _| '__/ _` | '_ ` _ \\| '_ \\ / _ \\ / _ \\| __| | | |/ _ \\| __| | __/ _ \\\n" +
                "| |_| | | | (_| | | | | | | |_) | (_) | (_) | |_| | | | (_) | |_| | ||  __/\n" +
                " \\____|_|  \\__,_|_| |_| |_|_.__/ \\___/ \\___/ \\__|_|_|_|\\___/ \\__|_|\\__\\___|\n" +
                "\n";
        getLogger().info(banner);
        getLogger().info("Gems Ultimate v" + getDescription().getVersion());
        getLogger().info("Authors: " + String.join(", ", getDescription().getAuthors()));
        getLogger().info("Server: " + Bukkit.getName() + " " + Bukkit.getBukkitVersion());
        Runtime rt = Runtime.getRuntime();
        long mb = 1024 * 1024;
        getLogger().info(String.format("Memory: free=%dMB total=%dMB max=%dMB",
                rt.freeMemory() / mb, rt.totalMemory() / mb, rt.maxMemory() / mb));
        getLogger().info("DataFolder: " + getDataFolder().getAbsolutePath());
    }

    public Gson getGson() {
        return gson;
    }

    public GemManager getGemManager() {
        return gemManager;
    }

    public StorageManager getStorageManager() {
        return storageManager;
    }

    public UISystem getUiSystem() {
        return uiSystem;
    }

    public AntiDuper getAntiDuper() {
        return antiDuper;
    }
  }
