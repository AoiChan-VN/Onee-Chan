package vn.aoi.onii.config;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import vn.aoi.onii.AoiPlugin;

import java.io.File;
import java.io.IOException;

public class ConfigManager {

    private final AoiPlugin plugin;

    private FileConfiguration config;
    private FileConfiguration messages;
    private FileConfiguration realms;
    private FileConfiguration mobs;

    private File messagesFile;
    private File realmsFile;
    private File mobsFile;

    public ConfigManager(AoiPlugin plugin) {
        this.plugin = plugin;
        load();
    }

    public void load() {

        // config.yml
        plugin.saveDefaultConfig();
        config = plugin.getConfig();

        // messages.yml
        messagesFile = new File(plugin.getDataFolder(), "messages.yml");
        if (!messagesFile.exists()) {
            plugin.saveResource("messages.yml", false);
        }
        messages = YamlConfiguration.loadConfiguration(messagesFile);

        // realms.yml
        realmsFile = new File(plugin.getDataFolder(), "realms.yml");
        if (!realmsFile.exists()) {
            plugin.saveResource("realms.yml", false);
        }
        realms = YamlConfiguration.loadConfiguration(realmsFile);

        // mobs.yml
        mobsFile = new File(plugin.getDataFolder(), "mobs.yml";
        if (!mobsFile.exists()) {
            plugin.saveResource("mobs.yml", false);
        }
        mobs = YamlConfiguration.loadConfiguration(mobsFile);
    }

    public void reload() {
        config = plugin.getConfig();
        messages = YamlConfiguration.loadConfiguration(messagesFile);
        realms = YamlConfiguration.loadConfiguration(realmsFile);
    }

    public void saveRealms() {
        try {
            realms.save(realmsFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ================== MESSAGES ==================

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

    // ================== GETTERS ==================

    public FileConfiguration getConfig() {
        return config;
    }

    public FileConfiguration getMessages() {
        return messages;
    }

    public FileConfiguration getRealms() {
        return realms;
    }

    public FileConfiguration getMobs() {
        return mobs;
    }
}
