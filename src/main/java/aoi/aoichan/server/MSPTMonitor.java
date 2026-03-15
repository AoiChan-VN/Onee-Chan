package aoi.aoichan.server;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;
import aoi.aoichan.AoiMain;

/*
 Theo dõi MSPT
*/

public class MSPTMonitor {

    private final AoiMain plugin;

    private BukkitTask task;

    private double mspt;

    public MSPTMonitor(AoiMain plugin) {
        this.plugin = plugin;
    }

    public void start() {

        task = Bukkit.getScheduler().runTaskTimer(plugin, () -> {

            mspt = Bukkit.getServer().getAverageTickTime();

        }, 20, 20);

    }

    public double getMSPT() {
        return mspt;
    }

    public void stop() {

        if (task != null)
            task.cancel();

    }

} 
