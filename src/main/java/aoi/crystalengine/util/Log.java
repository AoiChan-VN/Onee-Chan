package com.aoi.crystalengine.util;

import org.bukkit.Bukkit;

/*
#【!】Code:
Simple logger
*/

public class Log {

    public static void info(String msg) {
        Bukkit.getConsoleSender().sendMessage("[CrystalEngine] " + msg);
    }

    public static void success(String msg) {
        Bukkit.getConsoleSender().sendMessage("§a[CrystalEngine] " + msg);
    }

    public static void warn(String msg) {
        Bukkit.getConsoleSender().sendMessage("§e[CrystalEngine] " + msg);
    }

} 
