package vn.aoi.onii;

import org.bukkit.plugin.java.JavaPlugin;
import vn.aoi.onii.core.Bootstrap;

public class Main extends JavaPlugin {

    private static Main instance;

    @Override
    public void onEnable() {
        instance = this;
        Bootstrap.init(this);
    }

    @Override
    public void onDisable() {
        Bootstrap.shutdown();
    }

    public static Main getInstance() {
        return instance;
    }
} 
