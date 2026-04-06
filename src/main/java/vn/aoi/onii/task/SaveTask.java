package vn.aoi.onii.task;

import org.bukkit.scheduler.BukkitRunnable;
import vn.aoi.onii.manager.PlayerManager;

import java.util.UUID;

public class SaveTask extends BukkitRunnable {

    private final PlayerManager playerManager;

    public SaveTask(PlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    @Override
    public void run() {
        for (UUID uuid : playerManager.getOnlinePlayers().keySet()) {
            playerManager.savePlayer(uuid);
        }
    }
} 
