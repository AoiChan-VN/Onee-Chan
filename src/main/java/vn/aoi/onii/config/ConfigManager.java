package vn.aoi.onii.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import vn.aoi.onii.Main;

import java.io.File;

public class ConfigManager {

    public static FileConfiguration realms, mobs, messages;

    public static void init(Main plugin) {
        realms = load(plugin, "realms.yml");
        mobs = load(plugin, "mobs.yml");
        messages = load(plugin, "messages.yml");
    }

    private static FileConfiguration load(Main plugin, String name) {
        File file = new File(plugin.getDataFolder(), name);
        if (!file.exists()) plugin.saveResource(name, false);
        return YamlConfiguration.loadConfiguration(file);
    }
} 
