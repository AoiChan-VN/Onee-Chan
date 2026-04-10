package vn.aoi.onii;

import co.aikar.commands.PaperCommandManager;
import org.bukkit.plugin.java.JavaPlugin;

import vn.aoi.onii.commands.AoiCommand;
import vn.aoi.onii.commands.context.*;
import vn.aoi.onii.database.*;
import vn.aoi.onii.config.ConfigManager;
import vn.aoi.onii.listener.MobKillListener;
import vn.aoi.onii.manager.*;
import vn.aoi.onii.service.*;

public class AoiPlugin extends JavaPlugin {

    private static AoiPlugin instance;

    private ConfigManager configManager;
    
    private DatabaseManager database;
    private DatabaseExecutor executor;

    private RealmManager realmManager;
    private PlayerManager playerManager;
    private MobManager mobManager;

    private ExpService expService;
    private CultivationService cultivationService;
    
    @Override
    public void onEnable() {

        instance = this;

        saveDefaultConfig();

        // ================= CONFIG ===================
        
        configManager = new ConfigManager(this);

        // ================= DATABASE =================

        database = new DatabaseManager(getConfig());
        database.connect(getDataFolder());

        executor = new DatabaseExecutor();

        Migration.init(database);

        PlayerRepository repository = new PlayerRepository(database, executor);

        // ================= MANAGER =================

        realmManager = new RealmManager(configManager);
        playerManager = new PlayerManager(repository);
        mobManager = new MobManager(configManager);
        
        expService = new ExpService(playerManager, realmManager, configManager);
        cultivationService = new CultivationService(expService);

        getServer().getPluginManager().registerEvents(
            new MobKillListener(mobManager, cultivationService, configManager),
            this
        );

        // ================= ACF =================

        PaperCommandManager acf = new PaperCommandManager(this);

        ACFContext.register(acf);
        ACFCompletion.register(acf, realmManager);

        acf.registerCommand(new AoiCommand(playerManager));
 
        getLogger().info("▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬");
        getLogger().info("Aoi Plugin【ON】");
        getLogger().info("Tên: " + getDescription().getName());
        getLogger().info("Phiên bản: " + getDescription().getVersion());
        getLogger().info("Tác giả: " + getDescription().getAuthors());
        getLogger().info("▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬"); 
    }

    @Override
    public void onDisable() {

        if (executor != null) {
            executor.shutdown();
        }

        if (database != null) {
            database.close();
        }

        getLogger().info("▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬");
        getLogger().info("Aoi Plugin【OFF】");
        getLogger().info("Thanks my friend");
        getLogger().info("▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬");  
    }

    // =============== GETTER ===============

    public static AoiPlugin get() {
        return instance;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    public RealmManager getRealmManager() {
        return realmManager;
    }

    public CultivationService getCultivationService() {
        return cultivationService;
    }

    public ExpService getExpService() {
        return expService;
    }
    
    public MobManager getMobManager() {
        return mobManager;
    }
}
