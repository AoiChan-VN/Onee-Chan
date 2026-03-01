package aoichan.crystal;

import aoichan.crystal.api.GemsAPI;
import aoichan.crystal.commands.GemsCommand;
import aoichan.crystal.core.*;
import aoichan.crystal.gui.GUIListener;
import aoichan.crystal.storage.*;

import org.bukkit.plugin.java.JavaPlugin;

public final class AoiMain extends JavaPlugin {

    private static AoiMain instance;

    private DatabasePool databasePool;
    private StorageProvider storage;
    private GemsManager gemsManager;
    private SocketManager socketManager;
    private GemsAPI api;

    public static AoiMain get() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();
        saveResource("gems.yml", false);

        if (getConfig().getBoolean("banner.enabled", true)) {
            ConsoleBanner.show(this);
        }

        databasePool = new DatabasePool(this);
        storage = new SQLiteStorage(databasePool);
        storage.initTables();

        gemsManager = new GemsManager(this);
        socketManager = new SocketManager(this);
        api = new GemsAPI(gemsManager, socketManager);

        getCommand("gems").setExecutor(new GemsCommand());

        getServer().getPluginManager().registerEvents(
                new AntiDupeManager(socketManager), this);

        getServer().getPluginManager().registerEvents(
                new GUIListener(), this);
    }

    public GemsAPI getAPI() { return api; }
    public GemsManager getGemsManager() { return gemsManager; }

    @Override
    public void onDisable() {
        if (databasePool != null) databasePool.shutdown();
    }
}
