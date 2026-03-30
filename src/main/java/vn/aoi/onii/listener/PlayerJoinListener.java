package vn.aoi.onii.listener;

import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import vn.aoi.onii.AoiChan;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        AoiChan.getInstance().getDatabase().createPlayer(e.getPlayer().getUniqueId());
    }
} 
