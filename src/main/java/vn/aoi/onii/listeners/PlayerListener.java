package vn.aoi.onii.listeners;

import org.bukkit.event.*;
import org.bukkit.event.player.*;
import vn.aoi.onii.Main;

public class PlayerListener implements Listener {

    private final Main plugin;

    public PlayerListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        plugin.getPlayerManager().load(e.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        plugin.getPlayerManager().save(e.getPlayer());
    }
} 
