// File: config/ConfigManager.java
package vn.aoi.onii.config;

import org.bukkit.configuration.file.*;
import vn.aoi.onii.Main;

import java.io.File;

public class ConfigManager {

    private final Main plugin;
    private FileConfiguration config;

    public ConfigManager(Main plugin) {
        this.plugin = plugin;
    }

    public void loadAll() {
        File file = new File(plugin.getDataFolder(), "config.yml");
        if (!file.exists()) plugin.saveResource("config.yml", false);
        config = YamlConfiguration.loadConfiguration(file);
    }

    public FileConfiguration get() { return config; }
} 
