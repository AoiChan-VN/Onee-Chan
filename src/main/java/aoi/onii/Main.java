package vn.aoi.onii;

import org.bukkit.plugin.java.JavaPlugin;
import vn.aoi.onii.command.CommandManager;
import vn.aoi.onii.config.ConfigManager;
import vn.aoi.onii.database.DatabaseManager;
import vn.aoi.onii.listener.MobKillListener;
import vn.aoi.onii.service.CultivationService;

public class Main extends JavaPlugin {

    private static Main instance;
    private DatabaseManager databaseManager;
    private ConfigManager configManager;
    private CultivationService cultivationService;

    @Override
    public void onEnable() {
        instance = this;

        this.configManager = new ConfigManager(this);
        this.configManager.loadAll();

        this.databaseManager = new DatabaseManager(this);
        this.databaseManager.init();

        this.cultivationService = new CultivationService(this);

        new CommandManager(this).registerAll();

        getServer().getPluginManager().registerEvents(new MobKillListener(this), this);
    }

    @Override
    public void onDisable() {
        databaseManager.shutdown();
    }

    public static Main getInstance() { return instance; }
    public DatabaseManager getDatabaseManager() { return databaseManager; }
    public ConfigManager getConfigManager() { return configManager; }
    public CultivationService getCultivationService() { return cultivationService; }
}
