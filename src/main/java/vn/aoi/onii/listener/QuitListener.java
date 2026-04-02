package vn.aoi.onii.listener;

import org.bukkit.event.*;
import org.bukkit.event.player.PlayerQuitEvent;
import vn.aoi.onii.data.PlayerRepository;
import vn.aoi.onii.manager.PlayerManager;

public class QuitListener implements Listener {

    @EventHandler
    public void quit(PlayerQuitEvent e) {

        var data = PlayerManager.get(e.getPlayer().getUniqueId());
        if (data == null) return;

        PlayerRepository.saveAsync(data);
        PlayerManager.remove(data.getUuid());
    }
}
