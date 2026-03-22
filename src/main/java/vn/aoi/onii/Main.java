package vn.aoi.onii;

import org.bukkit.plugin.java.JavaPlugin;
import vn.aoi.onii.bootstrap.Bootstrap;

//【❅】 Main plugin entry
public class Main extends JavaPlugin {

    private static Main instance;
    private Bootstrap bootstrap;

    public static Main getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        //【❅】 Bootstrap toàn bộ hệ thống
        this.bootstrap = new Bootstrap(this);
        bootstrap.enable();

        getLogger().info("AoiRPG Enabled!");
    }

    @Override
    public void onDisable() {
        if (bootstrap != null) {
            bootstrap.disable();
        }

        getLogger().info("AoiRPG Disabled!");
    }
}
