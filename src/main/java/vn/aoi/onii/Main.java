package vn.aoi.onii;

import org.bukkit.plugin.java.JavaPlugin;
import vn.aoi.onii.commands.AoiCommand;
import vn.aoi.onii.database.Database;
import vn.aoi.onii.listeners.ChatListener;
import vn.aoi.onii.player.PlayerManager;

public class Main extends JavaPlugin {

    private static Main instance;
    private Database database;
    private PlayerManager playerManager;

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();

        database = new Database(this);
        database.connect();
        database.createTable();

        playerManager = new PlayerManager(database);

        getCommand("aoi").setExecutor(new AoiCommand(playerManager));
        getServer().getPluginManager().registerEvents(new ChatListener(playerManager), this);
    }

    @Override
    public void onDisable() {
        database.close();
    }

    public static Main getInstance() {
        return instance;
    }
}
