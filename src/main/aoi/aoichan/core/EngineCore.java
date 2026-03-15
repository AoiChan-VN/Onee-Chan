package aoi.aoichan.core;

import aoi.aoichan.AoiMain;

public class EngineCore {

    // 【!】Code: plugin instance
    private final AoiMain plugin;

    // 【!】Code: module loader
    private ModuleLoader moduleLoader;

    public EngineCore(AoiMain plugin){
        this.plugin = plugin;
    }

    public void start(){

        // 【!】Code: khởi tạo module loader
        moduleLoader = new ModuleLoader(plugin);

        plugin.getLogger().info("EngineCore started.");
    }

    public void shutdown(){

        if(moduleLoader != null){
            moduleLoader.disableModules();
        }

        plugin.getLogger().info("EngineCore stopped.");
    }

    public ModuleLoader getModuleLoader(){
        return moduleLoader;
    }

}
