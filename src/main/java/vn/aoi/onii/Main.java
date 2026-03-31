package vn.aoi.onii;

import org.bukkit.plugin.java.JavaPlugin;
import vn.aoi.onii.database.DatabaseManager;
import vn.aoi.onii.player.PlayerDataManager;

public class Main extends JavaPlugin {

    private static Main instance;
    private DatabaseManager databaseManager;
    private PlayerDataManager playerDataManager;

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();

        databaseManager = new DatabaseManager(this);
        databaseManager.init();

        playerDataManager = new PlayerDataManager(this);

        getServer().getPluginManager().registerEvents(playerDataManager, this);

        getLogger().info("AoiChan Enabled!");
    }

    @Override
    public void onDisable() {
        databaseManager.close();
        getLogger().info("AoiChan Disabled!");
    }

    public static Main getInstance() {
        return instance;
    }

    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    public PlayerDataManager getPlayerDataManager() {
        return playerDataManager;
    }
}
