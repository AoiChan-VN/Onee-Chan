package aoichan.crystal.platform.listener;

import aoichan.crystal.gameplay.stat.PlayerStatCache;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.Map;

public class CombatListener implements Listener {

    @EventHandler
    public void attack(EntityDamageByEntityEvent event) {

        if (!(event.getDamager() instanceof Player player))
            return;

        Map<String,Integer> stats =
                PlayerStatCache.get(player);

        int str = stats.getOrDefault("STR",0);

        // [!] Code: Apply strength
        event.setDamage(
                event.getDamage() + str
        );
    }
}
