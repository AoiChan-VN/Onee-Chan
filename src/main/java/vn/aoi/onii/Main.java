package vn.aoi.onii;

import org.bukkit.plugin.java.JavaPlugin;
import vn.aoi.onii.database.DatabaseManager;
import vn.aoi.onii.listener.PlayerJoinListener;
import vn.aoi.onii.command.CultivationCommand;

public class AoiChan extends JavaPlugin {

    private static AoiChan instance;
    private DatabaseManager database;

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();
        saveResource("messages.yml", false);

        database = new DatabaseManager(this);
        database.init();

        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
        getCommand("tuvi").setExecutor(new CultivationCommand());

        getLogger().info("AoiChan Enabled!");
    }

    public static AoiChan getInstance() {
        return instance;
    }

    public DatabaseManager getDatabase() {
        return database;
    }
}
