package vn.aoi.onii;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import co.aikar.commands.PaperCommandManager;

import vn.aoi.onii.commands.AoiCommand;
import vn.aoi.onii.api.AoiAPI;
import vn.aoi.onii.config.ConfigManager;
import vn.aoi.onii.config.MessageManager;
import vn.aoi.onii.database.PlayerRepository;
import vn.aoi.onii.database.SQLiteConnector;
import vn.aoi.onii.listener.ConnectionListener;
import vn.aoi.onii.listener.MobKillListener;
import vn.aoi.onii.manager.CultivationService;
import vn.aoi.onii.manager.PlayerManager;
import vn.aoi.onii.manager.RealmManager;
import vn.aoi.onii.task.SaveTask;

public class AoiPlugin extends JavaPlugin {

    private static AoiPlugin instance;

    private SQLiteConnector database;
    private PlayerRepository repository;

    private PlayerManager playerManager;
    private RealmManager realmManager;
    private CultivationService cultivationService;

    private ConfigManager configManager;
    private MessageManager messageManager;

    @Override
    public void onEnable() {
        instance = this;

        // 📦 Config
        this.configManager = new ConfigManager(this);
        this.messageManager = new MessageManager(this);

        // 🗄️ Database
        this.database = new SQLiteConnector(getDataFolder());
        this.repository = new PlayerRepository(database);

        // 🧠 Managers
        this.playerManager = new PlayerManager(repository);
        this.realmManager = new RealmManager(getConfig());
        this.cultivationService = new CultivationService(playerManager, realmManager);

        // ⚙️ Commands
        co.aikar.commands.PaperCommandManager acf = new co.aikar.commands.PaperCommandManager(this);

        // context
        vn.aoi.onii.commands.context.ACFContext.register(acf);

        // completion
        vn.aoi.onii.commands.context.ACFCompletion.register(acf, realmManager);

        // command
        acf.registerCommand(new vn.aoi.onii.commands.AoiCommand(playerManager, cultivationService));
      
        // 🌐 API
        new AoiAPI(playerManager);

        // 🔔 Register Listeners
        Bukkit.getPluginManager().registerEvents(
                new ConnectionListener(playerManager), this
        );

        Bukkit.getPluginManager().registerEvents(
                new MobKillListener(cultivationService, getConfig()), this
        );

        // ⏱️ Auto Save Task
        int interval = configManager.getAutoSaveInterval();

        new SaveTask(playerManager)
                .runTaskTimerAsynchronously(this, interval * 20L, interval * 20L);

        getLogger().info("AoiChan Plugin Enabled!");
    }

    @Override
    public void onDisable() {
        // 💾 Save all players
        playerManager.getOnlinePlayers().keySet()
                .forEach(playerManager::savePlayer);

        // 🔌 Close DB
        database.shutdown();

        getLogger().info("AoiChan Plugin Disabled!");
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
