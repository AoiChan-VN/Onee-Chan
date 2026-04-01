package vn.aoi.onii.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class BreakthroughEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private final Player player;
    private final String newRealm;

    public BreakthroughEvent(Player player, String newRealm) {
        this.player = player;
        this.newRealm = newRealm;
    }

    public Player getPlayer() { return player; }
    public String getNewRealm() { return newRealm; }

    @Override
    public HandlerList getHandlers() { return handlers; }
    public static HandlerList getHandlerList() { return handlers; }
} 
