package vn.aoi.onii;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private static Main instance;

    @Override
    public void onEnable() {
        instance = this;
        Bukkit.getLogger().info("AoiChan đã thức tỉnh linh lực!");
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info("AoiChan đã phi thăng...");
    }

    public static Main getInstance() {
        return instance;
    }
}
