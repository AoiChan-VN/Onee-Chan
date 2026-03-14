package aoichan.crystal.platform.listener;

import aoichan.crystal.gameplay.stat.StatUpdateService;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;

public class EquipmentChangeListener implements Listener {

    @EventHandler
    public void change(PlayerItemHeldEvent event) {

        // [!] Code: Update stats
        StatUpdateService.update(event.getPlayer());
    }
} 
