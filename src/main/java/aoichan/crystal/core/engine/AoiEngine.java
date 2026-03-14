package aoichan.crystal.core.engine;

import aoichan.crystal.infrastructure.config.ConfigManager;
import aoichan.crystal.infrastructure.util.LogUtil;
import org.bukkit.plugin.java.JavaPlugin;

public class AoiEngine {

    private final JavaPlugin plugin;

    public AoiEngine(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void start() {

        // 【!】Code: Load config
        ConfigManager.load(plugin);

        LogUtil.info("Crystal Engine Bật");

    }

    public void shutdown() {

        LogUtil.info("Crystal Engine Tắt");

    }

}
