package aoi.aoichan;

import aoi.aoichan.command.EngineCommand;
import aoi.aoichan.config.ConfigManager;
import aoi.aoichan.core.EngineCore;
import org.bukkit.plugin.java.JavaPlugin;

public final class AoiMain extends JavaPlugin {

    // 【!】Code: Singleton instance để plugin khác truy cập Engine
    private static AoiMain instance;

    // 【!】Code: Core engine
    private EngineCore engineCore;

    public static AoiMain getInstance() {
        return instance;
    }

    public EngineCore getEngineCore() {
        return engineCore;
    }

    @Override
    public void onEnable() {

        // 【!】Code: khởi tạo instance
        instance = this;

        // 【!】Code: load config
        ConfigManager.init(this);

        // 【!】Code: khởi động engine
        engineCore = new EngineCore(this);
        engineCore.start();

        // 【!】Code: register command
        getCommand("aoiengine").setExecutor(new EngineCommand());

        getLogger().info("AoiEngine Started.");
    }

    @Override
    public void onDisable() {

        // 【!】Code: shutdown engine
        if(engineCore != null){
            engineCore.shutdown();
        }

        getLogger().info("AoiEngine Shutdown.");
    }
}
