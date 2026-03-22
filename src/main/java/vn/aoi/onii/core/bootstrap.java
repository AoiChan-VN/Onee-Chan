// File: core/Bootstrap.java
package vn.aoi.onii.core;

import vn.aoi.onii.Main;
import vn.aoi.onii.config.ConfigManager;
import vn.aoi.onii.database.DatabaseManager;
import vn.aoi.onii.player.PlayerManager;
import vn.aoi.onii.scheduler.AsyncExecutor;
import vn.aoi.onii.command.CommandManager;

public class Bootstrap {

    private final Main plugin;

    private ConfigManager config;
    private AsyncExecutor executor;
    private DatabaseManager database;
    private PlayerManager playerManager;
    private CommandManager commandManager;

    public Bootstrap(Main plugin) {
        this.plugin = plugin;
    }

    public void init() {
        this.config = new ConfigManager(plugin);
        config.loadAll();

        this.executor = new AsyncExecutor();

        this.database = new DatabaseManager(plugin);
        database.init();

        this.playerManager = new PlayerManager(plugin, database, executor);
        playerManager.init();

        this.commandManager = new CommandManager(plugin, playerManager);
        commandManager.register();
    }

    public void shutdown() {
        playerManager.shutdown();
        database.shutdown();
        executor.shutdown();
    }
} 
