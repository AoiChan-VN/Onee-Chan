package aoi.aoichan;

import aoi.aoichan.api.EngineAPI;
import aoi.aoichan.command.CommandManager;
import aoi.aoichan.core.EngineBootstrap;
import aoi.aoichan.core.EngineConfig;
import aoi.aoichan.core.EngineLogger;
import aoi.aoichan.module.ModuleLoader;
import aoi.aoichan.reload.ReloadManager;
import org.bukkit.plugin.java.JavaPlugin;

/*
 CrystalEngine Core
 Paper 1.21.1
 Java 21
*/

public final class AoiMain extends JavaPlugin {

    private static AoiMain instance;

    private EngineLogger logger;
    private EngineConfig config;

    private ModuleLoader moduleLoader;
    private CommandManager commandManager;
    private ReloadManager reloadManager;

    private EngineBootstrap bootstrap;

    @Override
    public void onEnable() {

        // 【!】Code: lưu instance plugin
        instance = this;

        // 【!】Code: Khởi tạo logger của Engine
        engineLogger = new EngineLogger(getLogger());

        engineLogger.info("=================================");
        engineLogger.info("CrystalEngine đang khởi động...");
        engineLogger.info("Phiên bản: " + getDescription().getVersion());
        engineLogger.info("=================================");
        
        // 【!】Code: load config
        saveDefaultConfig();
        config = new EngineConfig(this);

        // 【!】Code: bootstrap engine
        bootstrap = new EngineBootstrap(this);
        bootstrap.start();

        // 【!】Code: khởi tạo module loader
        moduleLoader = new ModuleLoader(this);

        // 【!】Code: command system
        commandManager = new CommandManager(this);

        // 【!】Code: reload system
        reloadManager = new ReloadManager(this);

        // 【!】Code: đăng ký command
        commandManager.registerCommands();

        // 【!】Code: expose API
        EngineAPI.initialize(this);

        logger.info("CrystalEngine đã khởi động thành công.");
    }

    @Override
    public void onDisable() {

        // 【!】Code: tắt module
        if (moduleLoader != null) {
            moduleLoader.shutdownModules();
        }

        logger.info("CrystalEngine đã tắt.");
    }

    public static AoiMain getInstance() {
        return instance;
    }

    public EngineLogger getEngineLogger() {
        return logger;
    }

    public EngineConfig getEngineConfig() {
        return config;
    }

    public ModuleLoader getModuleLoader() {
        return moduleLoader;
    }

    public ReloadManager getReloadManager() {
        return reloadManager;
    }
}
