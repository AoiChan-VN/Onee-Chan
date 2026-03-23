package vn.aoi.onii.command;

import org.bukkit.command.CommandExecutor;
import vn.aoi.onii.Main;
import vn.aoi.onii.classsystem.ClassManager;
import vn.aoi.onii.player.PlayerManager;

public class CommandManager {

    private final Main plugin;
    private final PlayerManager playerManager;
    private final ClassManager classManager;

    public CommandManager(Main plugin, PlayerManager pm, ClassManager cm) {
        this.plugin = plugin;
        this.playerManager = pm;
        this.classManager = cm;
    }

    public void register() {
        plugin.getCommand("onii").setExecutor(new RootCommand(playerManager));
        plugin.getCommand("class").setExecutor(new ClassCommand(classManager));
    }
}
