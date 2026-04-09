package vn.aoi.onii;

import co.aikar.commands.PaperCommandManager;
import org.bukkit.plugin.java.JavaPlugin;

import vn.aoi.onii.commands.AoiCommand;
import vn.aoi.onii.commands.context.*;
import vn.aoi.onii.service.CultivationService;
import vn.aoi.onii.config.ConfigManager;
import vn.aoi.onii.database.*;
import vn.aoi.onii.manager.*;

public class AoiPlugin extends JavaPlugin {

    private static AoiPlugin instance;

    private ConfigManager configManager;
    
    private DatabaseManager database;
    private DatabaseExecutor executor;

    private RealmManager realmManager;
    private PlayerManager playerManager;
    private ExpService expService;
    private CultivationService cultivationService;
    private MobManager mobManager;
    
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

        playerManager = new PlayerManager();
        realmManager = new RealmManager(configManager);

        cultivationService = new CultivationService(expService);

        // ================ EXP | MOB ===============
        
        expService = new ExpService(playerManager, realmManager, configManager);
        mobManager = new MobManager(configManager);

        // ================= ACF =================

        PaperCommandManager acf = new PaperCommandManager(this);

        ACFContext.register(acf);
        ACFCompletion.register(acf, realmManager);

        acf.registerCommand(new AoiCommand(playerManager, cultivationService));

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

    public MobManager getMobManager() {
        return mobManager;
    }
}
