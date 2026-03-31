package vn.aoi.onii;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import vn.aoi.onii.database.DatabaseManager;
import vn.aoi.onii.player.PlayerDataManager;
import vn.aoi.onii.rank.RankManager;
import vn.aoi.onii.system.CultivationSystem;

public class Main extends JavaPlugin {

    private static Main instance;
    private DatabaseManager databaseManager;
    private PlayerDataManager playerDataManager;
    private RankManager rankManager;
    private CultivationSystem cultivationSystem;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        databaseManager = new DatabaseManager(this);
        databaseManager.init();

        rankManager = new RankManager(this);
        playerDataManager = new PlayerDataManager(this);
        cultivationSystem = new CultivationSystem(this);

        Bukkit.getPluginManager().registerEvents(playerDataManager, this);
        Bukkit.getPluginManager().registerEvents(cultivationSystem, this);

        getCommand("tuvi").setExecutor(playerDataManager);
        getCommand("dotpha").setExecutor(playerDataManager);

        getLogger().info("AoiChan Ultimate Loaded!");
    }

    @Override
    public void onDisable() {
        databaseManager.close();
    }

    public static Main getInstance() { return instance; }
    public DatabaseManager getDatabaseManager() { return databaseManager; }
    public PlayerDataManager getPlayerDataManager() { return playerDataManager; }
    public RankManager getRankManager() { return rankManager; }
    public CultivationSystem getCultivationSystem() { return cultivationSystem; }
}
