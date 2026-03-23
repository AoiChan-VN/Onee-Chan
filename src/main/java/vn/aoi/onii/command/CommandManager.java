// File: command/CommandManager.java
package vn.aoi.onii.command;

import org.bukkit.command.*;
import vn.aoi.onii.Main;
import vn.aoi.onii.player.PlayerManager;

public class CommandManager {

    private final Main plugin;
    private final PlayerManager playerManager;

    public CommandManager(Main plugin, PlayerManager pm) {
        this.plugin = plugin;
        this.playerManager = pm;
    }

    public void register() {
        plugin.getCommand("onii").setExecutor(new RootCommand(playerManager));
    }
}
