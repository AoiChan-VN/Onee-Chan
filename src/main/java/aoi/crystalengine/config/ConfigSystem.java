package com.aoi.crystalengine.config;

import org.bukkit.plugin.Plugin;

/*
#【!】Code:
Config loader + reload system
*/

public class ConfigSystem {

    private final Plugin plugin;

    public ConfigSystem(Plugin plugin) {

        this.plugin = plugin;
        plugin.saveDefaultConfig();
    }

    public void reload() {

        plugin.reloadConfig();
    }

} 
