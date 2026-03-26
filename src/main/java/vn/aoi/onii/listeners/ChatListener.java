package vn.aoi.onii.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import vn.aoi.onii.player.PlayerData;
import vn.aoi.onii.player.PlayerManager;

public class ChatListener implements Listener {

    private final PlayerManager manager;

    public ChatListener(PlayerManager manager) {
        this.manager = manager;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        PlayerData data = manager.getPlayer(e.getPlayer().getUniqueId(), e.getPlayer().getName());

        String format = "§7[ §e" + data.getCanhGioi() + " §7] §f" + e.getPlayer().getName() + ": " + e.getMessage();
        e.setFormat(format);
    }
}
