package vn.aoi.onii;

import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

import vn.aoi.onii.commands.core.CommandManager;
import vn.aoi.onii.commands.sub.HelpCommand;
import vn.aoi.onii.database.Database;
import vn.aoi.onii.listeners.ChatListener;
import vn.aoi.onii.quest.*;
import vn.aoi.onii.player.PlayerManager;

public class Main extends JavaPlugin {

    private static Main instance;

    private Database database;
    private PlayerManager playerManager;
    private QuestManager questManager;

    @Override
    public void onEnable() {
        instance = this;

        initDatabase();
        initManagers();
        registerCommands();
        registerListeners();

        getLogger().info("Onii plugin enabled!");
    }

    @Override
    public void onDisable() {
        if (database != null) {
            database.close();
        }

        if (playerManager != null) {
            playerManager.saveAll();
        }

        getLogger().info("Onii plugin disabled.");
    }

    // ================= DATABASE (ASYNC) =================
    private void initDatabase() {
        database = new Database(this);

        getServer().getScheduler().runTaskAsynchronously(this, () -> {
            try {
                database.connect();
                database.createTable();
                getLogger().info("Database connected!");
            } catch (Exception e) {
                getLogger().severe("Database failed!");
                e.printStackTrace();
                getServer().getPluginManager().disablePlugin(this);
            }
        });
    }

    // ================= MANAGERS =================
    private void initManagers() {
        playerManager = new PlayerManager(database);
        questManager = new QuestManager();
    }

    // ================= COMMANDS =================
    private void registerCommands() {
        CommandManager manager = new CommandManager();

        manager.register(new HelpCommand(manager));
        
        PluginCommand cmd = getCommand("aoi");

        if (cmd != null) {
            cmd.setExecutor(manager);
            cmd.setTabCompleter(manager);
        } else {
            getLogger().severe("Command 'aoi' not found in plugin.yml!");
        }
    }

    // ================= LISTENERS =================
    private void registerListeners() {
        getServer().getPluginManager().registerEvents(
                new ChatListener(playerManager), this
        );

        getServer().getPluginManager().registerEvents(
                new QuestListener(questManager, playerManager), this
        );
    }

    // ================= GETTERS =================
    public static Main getInstance() {
        return instance;
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    public QuestManager getQuestManager() {
        return questManager;
    }
}
