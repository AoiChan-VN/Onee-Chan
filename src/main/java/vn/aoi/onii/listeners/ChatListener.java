package vn.aoi.onii.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import vn.aoi.onii.player.PlayerManager;
import vn.aoi.onii.player.PlayerData;

public class ChatListener implements Listener {

    private final PlayerManager playerManager;

    public ChatListener(PlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        PlayerData data = playerManager.get(e.getPlayer());

        if (data == null) return;

        e.setFormat("[" + data.getRealm() + "] " +
                e.getPlayer().getName() + ": " +
                e.getMessage());
    }
}
