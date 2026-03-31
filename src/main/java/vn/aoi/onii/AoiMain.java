package vn.aoi.onii;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import vn.aoi.onii.data.Database;
import vn.aoi.onii.listener.PlayerListener;
import vn.aoi.onii.player.PlayerManager;
import vn.aoi.onii.rank.RankManager;
import vn.aoi.onii.system.ExpService;
import vn.aoi.onii.system.DoKiepService;

public final class AoiChanPlugin extends JavaPlugin {

    private static AoiChanPlugin instance;
    private Database database;
    private PlayerManager playerManager;
    private RankManager rankManager;
    private ExpService expService;
    private DoKiepService doKiepService;

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
        doKiepService = new DoKiepService(this);

        Bukkit.getPluginManager().registerEvents(new PlayerListener(this), this);

        getLogger().info("AoiChan FULL Enabled!");
    }

    @Override
    public void onDisable() {
        playerManager.shutdown();
        database.close();
    }

    public static AoiChanPlugin get() { return instance; }
    public Database getDatabase() { return database; }
    public PlayerManager getPlayerManager() { return playerManager; }
    public RankManager getRankManager() { return rankManager; }
    public ExpService getExpService() { return expService; }
    public DoKiepService getDoKiepService() { return doKiepService; }
}
