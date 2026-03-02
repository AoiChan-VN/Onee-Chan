package aoichan.crystal;

import aoichan.crystal.api.GemsAPI;
import aoichan.crystal.commands.GemsCommand;
import aoichan.crystal.core.*;
import aoichan.crystal.gui.GUIListener;
import aoichan.crystal.storage.*;
import aoichan.crystal.utils.PDCUtil;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class AoiMain extends JavaPlugin {

    private static AoiMain instance;

    private DatabasePool databasePool;
    private StorageProvider storage;
    private GemsManager gemsManager;
    private SocketManager socketManager;
    private GemsAPI api;

    // Refine
    private File refineFile;
    private FileConfiguration refineConfig;
    private RefineManager refineManager;

    public static AoiMain get() {
        return instance;
    }

    @Override
    public void onEnable() {

        instance = this;

        saveDefaultConfig();
        saveResource("gems.yml", false);

        // Save refine.yml
        refineFile = new File(getDataFolder(), "refine.yml");
        if (!refineFile.exists()) {
            saveResource("refine.yml", false);
        }
        refineConfig = YamlConfiguration.loadConfiguration(refineFile);

        // MUST init early
        PDCUtil.init(this);

        if (getConfig().getBoolean("banner.enabled", true)) {
            ConsoleBanner.show(this);
        }

        // ========================
        // Database & Storage
        // ========================

        databasePool = new DatabasePool(this);

        String type = getConfig().getString("storage.type", "SQLITE");
        if ("MYSQL".equalsIgnoreCase(type)) {
            storage = new MySQLStorage(databasePool);
        } else {
            storage = new SQLiteStorage(databasePool);
        }

        storage.initTables();

        // ========================
        // Managers & API
        // ========================

        gemsManager = new GemsManager(this);
        socketManager = new SocketManager(this);
        refineManager = new RefineManager(this);

        api = new GemsAPI(gemsManager, socketManager);

        // ========================
        // Commands & Events
        // ========================

        if (getCommand("gems") != null) {
            getCommand("gems").setExecutor(new GemsCommand(this));
        }

        getServer().getPluginManager().registerEvents(new GUIListener(this), this);
        getServer().getPluginManager().registerEvents(new AntiDupeManager(socketManager), this);

        getLogger().info("Crystal Ultimate loaded successfully.");
    }

    @Override
    public void onDisable() {

        if (storage != null) {
            storage.close();
        }

        if (databasePool != null) {
            databasePool.shutdown();
        }
    }

    // ========================
    // Getters
    // ========================

    public GemsManager getGemsManager() {
        return gemsManager;
    }

    public GemsAPI getAPI() {
        return api;
    }

    public RefineManager getRefineManager() {
        return refineManager;
    }

    public FileConfiguration getRefineConfig() {
        return refineConfig;
    }

    public void reloadRefineConfig() {
        refineConfig = YamlConfiguration.loadConfiguration(refineFile);
    }
}
