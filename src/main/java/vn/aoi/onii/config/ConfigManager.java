package vn.aoi.onii.config;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import vn.aoi.onii.AoiPlugin;

import java.io.File;

public class ConfigManager {

    private final AoiPlugin plugin;

    private FileConfiguration config;
    private FileConfiguration messages;

    public ConfigManager(AoiPlugin plugin) {
        this.plugin = plugin;
        load();
    }

    public void load() {

        plugin.saveDefaultConfig();
        config = plugin.getConfig();

        File msgFile = new File(plugin.getDataFolder(), "messages.yml");

        if (!msgFile.exists()) {
            plugin.saveResource("messages.yml", false);
        }

        messages = YamlConfiguration.loadConfiguration(msgFile);
    }

    public String getMessage(String path) {
        String msg = messages.getString(path, "&cMissing message: " + path);
        return color(msg);
    }

    public String getMessage(String path, String... placeholders) {

        String msg = getMessage(path);

        for (int i = 0; i < placeholders.length; i += 2) {
            msg = msg.replace(placeholders[i], placeholders[i + 1]);
        }

        return msg;
    }

    private String color(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public FileConfiguration getConfig() {
        return config;
    }
}
