package vn.aoi.onii;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import vn.aoi.onii.commands.AoiCommand;
import vn.aoi.onii.commands.AoiTabComplete;
import vn.aoi.onii.database.Database;
import vn.aoi.onii.listeners.ChatListener;
import vn.aoi.onii.player.PlayerManager;
import vn.aoi.onii.quest.QuestManager;
import vn.aoi.onii.quest.QuestListener;
import vn.aoi.onii.shop.ShopListener;

import java.io.File;

public class Main extends JavaPlugin {

    private static Main instance;

    private Database database;
    private PlayerManager playerManager;
    private QuestManager questManager;
    private Economy economy;

    private File shopFile;
    private FileConfiguration shopConfig;

    @Override
    public void onEnable() {
        instance = this;

        loadConfigs();

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

    // ================= CONFIG =================

    private void loadConfigs() {
        saveDefaultConfig();

        shopFile = new File(getDataFolder(), "shop.yml");
        if (!shopFile.exists()) {
            saveResource("shop.yml", false);
        }

        shopConfig = YamlConfiguration.loadConfiguration(shopFile);
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
        PluginCommand cmd = getCommand("aoi");

        if (cmd == null) {
            getLogger().severe("Command 'aoi' not found!");
            return;
        }

        cmd.setExecutor(new AoiCommand(playerManager));
        cmd.setTabCompleter(new AoiTabComplete());
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new ChatListener(playerManager), this);
        getServer().getPluginManager().registerEvents(new ShopListener(playerManager, economy), this);
        getServer().getPluginManager().registerEvents(new QuestListener(questManager, playerManager, economy), this);
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

    public FileConfiguration getShopConfig() {
        return shopConfig;
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
