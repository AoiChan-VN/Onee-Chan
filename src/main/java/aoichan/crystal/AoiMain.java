package aoichan.crystal;

import aoichan.crystal.api.GemsAPI;
import aoichan.crystal.commands.GemsCommand;
import aoichan.crystal.core.*;
import aoichan.crystal.gui.GUIListener;
import aoichan.crystal.storage.*;
import aoichan.crystal.utils.PDCUtil;
import org.bukkit.plugin.java.JavaPlugin;

public final class AoiMain extends JavaPlugin {

    private static AoiMain instance;

    private DatabasePool databasePool;
    private StorageProvider storage;
    private GemsManager gemsManager;
    private SocketManager socketManager;
    private GemsAPI api;

    public static AoiMain get() { return instance; }

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();
        saveResource("gems.yml", false);

        // init PDC key safely (NamespacedKey requires plugin instance)
        PDCUtil.init(this);

        if (getConfig().getBoolean("banner.enabled", true))
            ConsoleBanner.show(this);

        // setup DB pool and storage
        databasePool = new DatabasePool(this);
        String type = getConfig().getString("storage.type", "SQLITE");

        if ("MYSQL".equalsIgnoreCase(type)) {
            storage = new MySQLStorage(databasePool);
        } else {
            storage = new SQLiteStorage(databasePool);
        }
        storage.initTables();

        // managers
        gemsManager = new GemsManager(this);
        socketManager = new SocketManager(this);
        api = new GemsAPI(gemsManager, socketManager);

        // commands & events
        if (getCommand("gems") != null) getCommand("gems").setExecutor(new GemsCommand(this));
        getServer().getPluginManager().registerEvents(new GUIListener(this), this);
        getServer().getPluginManager().registerEvents(new AntiDupeManager(socketManager), this);

        getLogger().info("GemsUltimate loaded.");
    }

    @Override
    public void onDisable() {
        if (storage != null) storage.close();
        if (databasePool != null) databasePool.shutdown();
    }

    public GemsManager getGemsManager() { return gemsManager; }
    public GemsAPI getAPI() { return api; }
}
