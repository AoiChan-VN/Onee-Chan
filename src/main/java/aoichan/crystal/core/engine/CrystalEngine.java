package aoichan.crystal.core.engine;

import aoichan.crystal.infrastructure.config.ConfigManager;
import aoichan.crystal.infrastructure.util.LogUtil;
import org.bukkit.plugin.java.JavaPlugin;

public class CrystalEngine {

    private final JavaPlugin plugin;

    public CrystalEngine(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void start() {

        // 【!】Code: Load config
        ConfigManager.load(plugin);

        LogUtil.info("Engine đang bật");

    }

    public void shutdown() {

        LogUtil.info("Engine đang tắt");

    }

}
