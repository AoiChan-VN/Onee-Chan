package vn.aoi.onii.listener;

import org.bukkit.event.*;
import org.bukkit.event.entity.EntityDeathEvent;
import vn.aoi.onii.config.ConfigManager;
import vn.aoi.onii.manager.*;

public class MobKillListener implements Listener {

    @EventHandler
    public void kill(EntityDeathEvent e) {

        var p = e.getEntity().getKiller();
        if (p == null) return;

        var type = e.getEntity().getType().name();
        if (!ConfigManager.mobs.contains(type)) return;

        var data = PlayerManager.get(p.getUniqueId());
        if (data == null) return;

        data.addExp(ConfigManager.mobs.getInt(type));
        LevelManager.check(p, data);
    }
} 
