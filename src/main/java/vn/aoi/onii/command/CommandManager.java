package vn.aoi.onii.command;

import org.bukkit.command.*;
import vn.aoi.onii.Main;

import vn.aoi.onii.player.PlayerManager;
import vn.aoi.onii.classsystem.ClassManager;

public class CommandManager {

    private final Main plugin;
    private final PlayerManager playerManager;
    private final ClassManager ClassManager;

    public CommandManager(Main plugin, PlayerManager pm, ClassManager cl) {
        this.plugin = plugin;
        this.playerManager = pm;
        this.classManager = cl;
    }

    public void register() {
        plugin.getCommand("onii").setExecutor(new RootCommand(playerManager));
        plugin.getCommand("class").setExecutor(new ClassCommand(classManager));
    }
}
