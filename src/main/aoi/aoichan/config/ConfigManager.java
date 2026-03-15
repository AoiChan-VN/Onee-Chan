package aoi.aoichan.config;

import org.bukkit.plugin.java.JavaPlugin;

public class ConfigManager {

    private static JavaPlugin plugin;

    public static void init(JavaPlugin pl){

        plugin = pl;

        plugin.saveDefaultConfig();

    }

    // 【!】Code: reload config không restart server
    public static void reload(){

        plugin.reloadConfig();

    }

} 
