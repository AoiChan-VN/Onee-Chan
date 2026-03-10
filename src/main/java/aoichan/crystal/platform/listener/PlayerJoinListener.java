package aoichan.crystal.platform.listener;

import aoichan.crystal.gameplay.stat.StatUpdateService;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void join(PlayerJoinEvent event) {

        // [!] Code: Update stats
        StatUpdateService.update(event.getPlayer());
    }
} 
