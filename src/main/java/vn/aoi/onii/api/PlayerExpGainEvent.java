package vn.aoi.onii.api;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter
public class PlayerExpGainEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    private final Player player;

    @Setter
    private double amount;

    public PlayerExpGainEvent(Player player, double amount) {
        this.player = player;
        this.amount = amount;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
