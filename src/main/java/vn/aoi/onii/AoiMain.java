package vn.aoi.onii;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import vn.aoi.onii.data.Database;
import vn.aoi.onii.listener.PlayerListener;
import vn.aoi.onii.player.PlayerManager;
import vn.aoi.onii.rank.RankManager;
import vn.aoi.onii.system.ExpService;
import vn.aoi.onii.system.ThienKiepService;

public final class AoiMain extends JavaPlugin {

    private static AoiMain instance;
    private Database database;
    private PlayerManager playerManager;
    private RankManager rankManager;
    private ExpService expService;
    private ThienKiepService thienKiepService;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        database = new Database(this);
        database.init();

        rankManager = new RankManager(this);
        rankManager.load();

        playerManager = new PlayerManager(this);
        expService = new ExpService(this);
        thienKiepService = new ThienKiepService(this);

        Bukkit.getPluginManager().registerEvents(new PlayerListener(this), this);

        getLogger().info("AoiChan Enabled!");
    }

    @Override
    public void onDisable() {
        playerManager.shutdown();
        database.close();
    }

    public static AoiMain get() { return instance; }
    public Database getDatabase() { return database; }
    public PlayerManager getPlayerManager() { return playerManager; }
    public RankManager getRankManager() { return rankManager; }
    public ExpService getExpService() { return expService; }
    public ThienKiepService getThienKiepService() { return thienKiepService; }
}
