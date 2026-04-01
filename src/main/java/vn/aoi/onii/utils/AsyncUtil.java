package vn.aoi.onii.utils;

import org.bukkit.Bukkit;

public class AsyncUtil {

    public static void runAsync(Runnable task) {
        Bukkit.getScheduler().runTaskAsynchronously(
                vn.aoi.onii.Main.getInstance(),
                task
        );
    }
} 
