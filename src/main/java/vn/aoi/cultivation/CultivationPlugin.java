package vn.aoi.cultivation;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.HandlerList;

public class CultivationPlugin extends JavaPlugin {

    private static CultivationPlugin instance;

    @Override
    public void onEnable() {
        instance = this;

        // Load default configs (CI/CD safe)
        saveDefaultConfig();

        getLogger().info("[CultivationPlugin] System booting...");

        // Register commands already defined in plugin.yml (handled here manually fallback safety)
        getLogger().info("[CultivationPlugin] Command layer initialized.");

        // Placeholder: Listener registration will be injected in next steps
        getLogger().info("[CultivationPlugin] Listener layer pending module initialization.");

        // Safety tick watchdog (lightweight anti-freeze heartbeat)
        Bukkit.getScheduler().runTaskTimer(this, () -> {
            // heartbeat tick - can extend later
        }, 20L, 20L);

        getLogger().info("[CultivationPlugin] ENABLED successfully.");
    }

    @Override
    public void onDisable() {

        getLogger().info("[CultivationPlugin] Shutting down system...");

        // Cancel all scheduled tasks (anti-leak requirement)
        Bukkit.getScheduler().cancelTasks(this);

        // Unregister all events
        HandlerList.unregisterAll(this);

        instance = null;

        getLogger().info("[CultivationPlugin] DISABLED cleanly.");
    }

    public static CultivationPlugin getInstance() {
        return instance;
    }

    /**
     * Fallback command handler (safe until command framework is built)
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (command.getName().equalsIgnoreCase("cultivation")) {

            sender.sendMessage("§a[Cultivation] System is alive.");
            sender.sendMessage("§7Core engine loaded. Modules incoming...");

            return true;
        }

        return false;
    }
}
