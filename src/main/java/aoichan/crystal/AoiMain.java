package aoichan.crystal;

import aoichan.crystal.api.GemsAPI;
import aoichan.crystal.core.*;
import aoichan.crystal.gui.*;
import aoichan.crystal.storage.*;
import aoichan.crystal.commands.GemsCommand;
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

        if (getConfig().getBoolean("banner.enabled"))
            ConsoleBanner.show(this);

        setupDatabase();
        setupManagers();
        registerSystems();

        getLogger().info("GemsUltimate 2.0 loaded safely.");
    }

    private void setupDatabase() {
        databasePool = new DatabasePool(this);
        storage = databasePool.createProvider();
        storage.initTables();
    }

    private void setupManagers() {
        gemsManager = new GemsManager(this);
        socketManager = new SocketManager(this);
        api = new GemsAPI(gemsManager, socketManager);
    }

    private void registerSystems() {
        getCommand("gems").setExecutor(new GemsCommand());
        getServer().getPluginManager().registerEvents(
                new AntiDupeManager(socketManager), this);
        getServer().getPluginManager().registerEvents(
                new GUIListener(), this);
    }

    public GemsAPI getAPI() { return api; }
    public StorageProvider getStorage() { return storage; }

    @Override
    public void onDisable() {
        if (databasePool != null) databasePool.shutdown();
    }
}
