package vn.aoi.onii;

import org.bukkit.plugin.java.JavaPlugin;
import vn.aoi.onii.command.CommandManager;
import vn.aoi.onii.config.ConfigManager;
import vn.aoi.onii.data.Database;
import vn.aoi.onii.listener.*;
import vn.aoi.onii.manager.RealmManager;
import vn.aoi.onii.task.AutoSaveTask;

public class Main extends JavaPlugin {

    private static Main instance;
    private Database database;

    @Override
    public void onEnable() {
        instance = this;

        ConfigManager.init(this);
        RealmManager.load();

        database = new Database(this);
        database.connect();

        CommandManager.init(this);

        getServer().getPluginManager().registerEvents(new JoinListener(), this);
        getServer().getPluginManager().registerEvents(new QuitListener(), this);
        getServer().getPluginManager().registerEvents(new MobKillListener(), this);

        new AutoSaveTask().runTaskTimer(this, 6000, 6000);
    }

    @Override
    public void onDisable() {
        database.close();
    }

    public static Main getInstance() {
        return instance;
    }

    public Database getDatabase() {
        return database;
    }
}
