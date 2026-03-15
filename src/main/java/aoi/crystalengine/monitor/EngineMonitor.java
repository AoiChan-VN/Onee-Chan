package com.aoi.crystalengine.monitor;

import org.bukkit.Bukkit;

/*
#【!】Code:
Server performance monitor
*/

public class EngineMonitor {

    public double getTPS() {

        try {

            return Bukkit.getServer().getTPS()[0];

        } catch (Throwable t) {

            return -1;
        }

    }

    public long getUsedMemory() {

        Runtime r = Runtime.getRuntime();

        return r.totalMemory() - r.freeMemory();
    }

} 
