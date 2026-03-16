package aoi.aoichan;

import aoi.aoichan.engine.core.Engine;
import aoi.aoichan.engine.core.EngineBootstrap;
import aoi.aoichan.engine.util.EngineLogger;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * AoiMain
 *
 * Đây là entry point của toàn bộ AoiEngine MMORPG system.
 *
 * Paper sẽ load class này khi server khởi động.
 *
 * Nhiệm vụ:
 * - Khởi tạo EngineBootstrap
 * - Start Engine Core Systems
 * - Shutdown engine khi plugin disable
 */
public final class AoiMain extends JavaPlugin {

    private static AoiMain instance;
    private Engine engine;

    /**
     * Lấy instance plugin global
     */
    public static AoiMain getInstance() {
        return instance;
    }

    /**
     * Lấy engine instance
     */
    public Engine getEngine() {
        return engine;
    }

    @Override
    public void onEnable() {

        instance = this;

        EngineLogger.info("=================================");
        EngineLogger.info("       AOIENGINE BOOTING         ");
        EngineLogger.info("=================================");

        try {

            EngineBootstrap bootstrap = new EngineBootstrap(this);
            this.engine = bootstrap.boot();

            EngineLogger.info("AoiEngine successfully started.");

        } catch (Exception e) {

            EngineLogger.error("Engine failed to start.");
            e.printStackTrace();
            getServer().getPluginManager().disablePlugin(this);

        }
    }

    @Override
    public void onDisable() {

        EngineLogger.info("Shutting down AoiEngine...");

        if (engine != null) {
            engine.shutdown();
        }

        EngineLogger.info("AoiEngine stopped.");
    }
}
