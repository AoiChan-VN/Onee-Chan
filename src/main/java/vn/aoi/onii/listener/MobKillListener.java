package vn.aoi.onii.listener;

import org.bukkit.event.*;
import org.bukkit.event.entity.EntityDeathEvent;
import vn.aoi.onii.config.ConfigManager;
import vn.aoi.onii.manager.*;
import vn.aoi.onii.data.PlayerData;

public class MobKillListener implements Listener {

    @EventHandler
    public void kill(EntityDeathEvent e){
        var p=e.getEntity().getKiller();
        if(p==null) return;

        String type=e.getEntity().getType().name();
        if(!ConfigManager.mobs.contains(type)) return;

        PlayerData d=PlayerManager.get(p.getUniqueId());
        if(d==null) return;

        d.exp+=ConfigManager.mobs.getInt(type);
        LevelManager.check(p,d);
    }
}
