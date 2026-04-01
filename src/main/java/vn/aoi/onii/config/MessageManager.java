package vn.aoi.onii.config;

import org.bukkit.configuration.file.FileConfiguration;
import vn.aoi.onii.Main;

public class MessageManager {

    private static FileConfiguration config;

    public static void init(Main plugin) {
        plugin.saveResource("messages.yml", false);
        config = plugin.getConfig(); // hoặc load riêng file nếu muốn
    }

    public static String get(String path) {
        return config.getString("messages." + path, "Missing message: " + path);
    }
} 
