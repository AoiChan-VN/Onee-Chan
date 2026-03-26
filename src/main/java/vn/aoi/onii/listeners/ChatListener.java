package vn.aoi.onii.listeners;

import org.bukkit.event.*;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import vn.aoi.onii.player.PlayerManager;

public class ChatListener implements Listener {

    private final PlayerManager manager;

    public ChatListener(PlayerManager manager) {
        this.manager = manager;
    }

    @EventHandler
    public void chat(AsyncPlayerChatEvent e) {
        var data = manager.get(e.getPlayer().getUniqueId(), e.getPlayer().getName());

        e.setFormat("[ " + data.getRealm().getDisplay() + " ] " +
                e.getPlayer().getName() + ": " + e.getMessage());
    }
}
