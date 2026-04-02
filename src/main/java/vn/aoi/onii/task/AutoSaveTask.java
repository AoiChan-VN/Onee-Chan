package vn.aoi.onii.task;

import org.bukkit.scheduler.BukkitRunnable;
import vn.aoi.onii.manager.PlayerManager;
import vn.aoi.onii.data.PlayerRepository;

public class AutoSaveTask extends BukkitRunnable {

    public void run(){
        PlayerManager.all().forEach(PlayerRepository::save);
    }
} 
