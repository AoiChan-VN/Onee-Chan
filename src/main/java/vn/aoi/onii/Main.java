package vn.aoi.onii;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import vn.aoi.onii.commands.*;
import vn.aoi.onii.database.Database;
import vn.aoi.onii.listeners.ChatListener;
import vn.aoi.onii.player.PlayerManager;
import vn.aoi.onii.quest.*;
import vn.aoi.onii.shop.ShopListener;

import java.io.File;

public class Main extends JavaPlugin {

    private static Main instance;
    private Database database;
    private PlayerManager playerManager;
    private Economy econ;
    private QuestManager questManager;

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();
        saveResource("shop.yml", false);
        saveResource("quests.yml", false);

        setupEconomy();

        database = new Database(this);
        database.connect();
        database.createTable();

        playerManager = new PlayerManager(database);
        questManager = new QuestManager();

        getCommand("aoi").setExecutor(new AoiCommand(playerManager));
        getCommand("aoi").setTabCompleter(new AoiTabComplete());
        
        getServer().getPluginManager().registerEvents(new ChatListener(playerManager), this);
        getServer().getPluginManager().registerEvents(new ShopListener(playerManager, econ), this);
        getServer().getPluginManager().registerEvents(new QuestListener(questManager, playerManager, econ), this);
    }

    @Override
    public void onDisable() {
        database.close();
    }

    public static Main getInstance() {
        return instance;
    }

    public File getShopFile() {
        return new File(getDataFolder(), "shop.yml");
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) return false;

        RegisteredServiceProvider<Economy> rsp =
                getServer().getServicesManager().getRegistration(Economy.class);

        if (rsp == null) return false;

        econ = rsp.getProvider();
        return econ != null;
    }

    public Economy getEcon() {
        return econ;
    }

    public Database getDatabase() {
        return database;
    }
}
