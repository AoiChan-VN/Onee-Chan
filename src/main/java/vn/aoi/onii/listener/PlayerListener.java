package vn.aoi.onii.listener;

import org.bukkit.event.*;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.*;
import vn.aoi.onii.AoiMain;

public class PlayerListener implements Listener {

    private final AoiMain plugin;

    public PlayerListener(AoiMain plugin){this.plugin=plugin;}

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        plugin.getPlayerManager().load(e.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        plugin.getPlayerManager().save(e.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onKill(EntityDeathEvent e){
        if(e.getEntity().getKiller()!=null){
            plugin.getExpService().addExp(e.getEntity().getKiller(),50);
        }
    }
}
