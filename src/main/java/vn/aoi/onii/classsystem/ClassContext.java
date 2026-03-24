package vn.aoi.onii.classsystem;

import org.bukkit.entity.Player;
import vn.aoi.onii.player.PlayerData;
import vn.aoi.onii.combat.BuffManager;

public class ClassContext {

    private final Player player;
    private final PlayerData data;
    private final BuffManager buffManager;

    public ClassContext(Player player, PlayerData data, BuffManager buffManager) {
        this.player = player;
        this.data = data;
        this.buffManager = buffManager;
    }

    public Player getPlayer() {
        return player;
    }

    public PlayerData getData() {
        return data;
    }

    public BuffManager getBuffManager() {
        return buffManager;
    }
}
