package vn.aoi.onii.api;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter
public class PlayerLevelUpEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    private final Player player;
    private final String oldRealm;
    private final String newRealm;
    private final int newLevel;

    public PlayerLevelUpEvent(Player player, String oldRealm, String newRealm, int newLevel) {
        this.player = player;
        this.oldRealm = oldRealm;
        this.newRealm = newRealm;
        this.newLevel = newLevel;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
