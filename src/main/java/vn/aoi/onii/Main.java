package vn.aoi.onii;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import vn.aoi.onii.commands.*;
import vn.aoi.onii.database.Database;
import vn.aoi.onii.listeners.ChatListener;
import vn.aoi.onii.quest.*;
import vn.aoi.onii.player.PlayerManager;

public class Main extends JavaPlugin {

    private static Main instance;

    private Database database;
    private PlayerManager playerManager;
    private QuestManager questManager;
    private Economy economy;

    @Override
    public void onEnable() {
        instance = this;

        if (!setupEconomy()) {
            getLogger().severe("Vault not found! Disabling plugin...");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

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
        getLogger().info("Onii plugin disabled.");
    }

    // ================= INIT =================

    private void initDatabase() {
        database = new Database(this);
        database.connect();
        database.createTable();
    }

    private void initManagers() {
        playerManager = new PlayerManager(database);
        questManager = new QuestManager();
    }

    private void registerCommands() {
        CommandManager manager = new CommandManager();
        manager.register(new HelpCommand());

        PluginCommand cmd = getCommand("aoi");
        if (cmd != null) {
            cmd.setExecutor(manager);
            cmd.setTabCompleter(manager);
        }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(
                new ChatListener(playerManager), this
        );

        getServer().getPluginManager().registerEvents(
                new QuestListener(questManager, playerManager, economy), this
        );
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }

        RegisteredServiceProvider<Economy> rsp =
                getServer().getServicesManager().getRegistration(Economy.class);

        if (rsp == null) return false;

        economy = rsp.getProvider();
        return economy != null;
    }

    // ================= GETTER =================

    public static Main getInstance() {
        return instance;
    }

    public Economy getEconomy() {
        return economy;
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    public QuestManager getQuestManager() {
        return questManager;
    }
}
