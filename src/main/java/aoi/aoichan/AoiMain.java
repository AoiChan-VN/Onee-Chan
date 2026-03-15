
package aoi.aoichan;

import aoi.aoichan.core.EngineBootstrap;
import aoi.aoichan.core.EngineConfig;
import aoi.aoichan.core.EngineLogger;
import org.bukkit.plugin.java.JavaPlugin;

/*
 * CrystalEngine - Core Plugin
 * Paper 1.21.1
 * Java 21
 */

public final class AoiMain extends JavaPlugin {

    private static AoiMain instance;

    private EngineBootstrap bootstrap;
    private EngineConfig engineConfig;
    private EngineLogger engineLogger;

    @Override
    public void onEnable() {

        // 【!】Code: Khởi tạo instance plugin
        instance = this;

        // 【!】Code: Khởi tạo logger của Engine
        engineLogger = new EngineLogger(getLogger());

        engineLogger.info("=================================");
        engineLogger.info("CrystalEngine đang khởi động...");
        engineLogger.info("Phiên bản: " + getDescription().getVersion());
        engineLogger.info("=================================");

        // 【!】Code: Load config
        saveDefaultConfig();
        engineConfig = new EngineConfig(this);

        // 【!】Code: Bootstrap hệ thống Engine
        bootstrap = new EngineBootstrap(this);
        bootstrap.start();

        engineLogger.info("CrystalEngine đã khởi động thành công!");
    }

    @Override
    public void onDisable() {

        // 【!】Code: Shutdown engine
        if (bootstrap != null) {
            bootstrap.shutdown();
        }

        engineLogger.info("CrystalEngine đã tắt.");
    }

    // 【!】Code: Getter Instance
    public static AoiMain getInstance() {
        return instance;
    }

    // 【!】Code: Getter Config
    public EngineConfig getEngineConfig() {
        return engineConfig;
    }

    // 【!】Code: Getter Logger
    public EngineLogger getEngineLogger() {
        return engineLogger;
    }
}
