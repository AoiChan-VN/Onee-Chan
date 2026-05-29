package com.aoi.cultivation;

import com.aoi.cultivation.config.ConfigManager;
import com.aoi.cultivation.data.PlayerDataManager;
import com.aoi.cultivation.data.SQLiteManager;
import com.aoi.cultivation.listeners.CombatExpListener;
import com.aoi.cultivation.listeners.ConnectionListener;
import com.aoi.cultivation.listeners.GUIExploitListener;
import com.aoi.cultivation.menu.ProfessionMenu;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class AoiCultivationPlugin extends JavaPlugin {

    private static AoiCultivationPlugin instance;
    private SQLiteManager sqliteManager;
    private PlayerDataManager playerDataManager;
    private ConfigManager configManager;

    public static AoiCultivationPlugin getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();

        configManager = new ConfigManager(this);
        sqliteManager = new SQLiteManager(this);
        playerDataManager = new PlayerDataManager(sqliteManager);

        sqliteManager.connect();
        playerDataManager.init();

        Bukkit.getPluginManager().registerEvents(new ConnectionListener(playerDataManager), this);
        Bukkit.getPluginManager().registerEvents(new CombatExpListener(playerDataManager), this);
        Bukkit.getPluginManager().registerEvents(new GUIExploitListener(), this);

        getCommand("cultivation").setExecutor(new com.aoi.cultivation.commands.CultivationCommand(playerDataManager));

        getLogger().info("Aoi Cultivation initialized.");
    }

    @Override
    public void onDisable() {
        Bukkit.getScheduler().cancelTasks(this);
        Bukkit.getPluginManager().registerEvents(null, this);
        sqliteManager.disconnect();
    }
} 
