package vn.aoi.onii.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import vn.aoi.onii.config.ConfigManager;
import vn.aoi.onii.manager.LevelManager;
import vn.aoi.onii.manager.PlayerManager;

public class MobKillListener implements Listener {

    @EventHandler
    public void onKill(EntityDeathEvent e) {
        if (e.getEntity().getKiller() == null) return;

        var player = e.getEntity().getKiller();
        var type = e.getEntity().getType().name();

        if (!ConfigManager.mobs.contains(type)) return;

        var data = PlayerManager.get(player.getUniqueId());
        if (data == null) return;

        data.addExp(ConfigManager.mobs.getInt(type));

        LevelManager.check(player, data);
    }
}
