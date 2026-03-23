package vn.aoi.onii.classsystem;

import org.bukkit.entity.Player;
import vn.aoi.onii.player.PlayerData;

public class ClassContext {

    private final Player player;
    private final PlayerData data;

    public ClassContext(Player player, PlayerData data) {
        this.player = player;
        this.data = data;
    }

    public Player getPlayer() { return player; }
    public PlayerData getData() { return data; }
}
