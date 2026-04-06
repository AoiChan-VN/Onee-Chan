package vn.aoi.onii;

import co.aikar.commands.PaperCommandManager;
import org.bukkit.plugin.java.JavaPlugin;

import vn.aoi.onii.commands.AoiCommand;
import vn.aoi.onii.commands.context.*;
import vn.aoi.onii.database.DatabaseExecutor;
import vn.aoi.onii.database.DatabaseManager;
import vn.aoi.onii.database.Migration;
import vn.aoi.onii.database.PlayerRepository;
import vn.aoi.onii.manager.CultivationService;
import vn.aoi.onii.manager.PlayerManager;
import vn.aoi.onii.manager.RealmManager;

public class AoiPlugin extends JavaPlugin {

    private static AoiPlugin instance;

    private DatabaseManager database;
    private DatabaseExecutor executor;

    private PlayerManager playerManager;
    private CultivationService cultivationService;
    private RealmManager realmManager;

    @Override
    public void onEnable() {

        instance = this;

        saveDefaultConfig();

        // ================= DATABASE =================

        database = new DatabaseManager(getConfig());
        database.connect(getDataFolder());

        executor = new DatabaseExecutor();

        Migration.init(database);

        PlayerRepository repository = new PlayerRepository(database, executor);

        // ================= MANAGER =================

        playerManager = new PlayerManager(repository);
        realmManager = new RealmManager(getConfig());
        cultivationService = new CultivationService(playerManager, realmManager);

        // ================= ACF =================

        PaperCommandManager acf = new PaperCommandManager(this);

        ACFContext.register(acf);
        ACFCompletion.register(acf, realmManager);

        acf.registerCommand(new AoiCommand(playerManager, cultivationService));

        getLogger().info("Aoi Plugin【ON】");
    }

    @Override
    public void onDisable() {

        if (executor != null) {
            executor.shutdown();
        }

        if (database != null) {
            database.close();
        }

        getLogger().info("Aoi Plugin【OFF】");
    }

    public static AoiPlugin get() {
        return instance;
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    public CultivationService getCultivationService() {
        return cultivationService;
    }

    public RealmManager getRealmManager() {
        return realmManager;
    }
}
