package aoichan.crystal;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * Core listeners (expand for actual gameplay hooks).
 */
public class CoreListeners implements Listener {

    private final Main plugin;
    private final GemManager gemManager;
    private final AntiDuper antiDuper;

    public CoreListeners(Main plugin, GemManager gemManager, AntiDuper antiDuper) {
        this.plugin = plugin;
        this.gemManager = gemManager;
        this.antiDuper = antiDuper;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        // route to UI system
        plugin.getUiSystem().handleClick(event);
    }
}
