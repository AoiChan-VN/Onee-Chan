package com.aoi.crystalengine.bootstrap;

import com.aoi.crystalengine.CrystalEngine;
import com.aoi.crystalengine.module.ModuleManager;
import com.aoi.crystalengine.service.ServiceRegistry;
import com.aoi.crystalengine.player.PlayerRegistry;
import com.aoi.crystalengine.scheduler.EngineScheduler;
import com.aoi.crystalengine.data.DataRegistry;
import com.aoi.crystalengine.config.ConfigSystem;

/*
#【!】Code:
Bootstrap khởi tạo toàn bộ Engine systems.
*/

public class EngineBootstrap {

    private final CrystalEngine plugin;

    private ModuleManager moduleManager;
    private ServiceRegistry serviceRegistry;
    private PlayerRegistry playerRegistry;
    private DataRegistry dataRegistry;
    private EngineScheduler scheduler;
    private ConfigSystem configSystem;

    public EngineBootstrap(CrystalEngine plugin) {
        this.plugin = plugin;
    }

    public void start() {

        configSystem = new ConfigSystem(plugin);

        serviceRegistry = new ServiceRegistry();
        moduleManager = new ModuleManager();

        playerRegistry = new PlayerRegistry();
        dataRegistry = new DataRegistry();

        scheduler = new EngineScheduler(plugin);

    }

    public void shutdown() {

        scheduler.shutdown();
    }

    public ModuleManager modules() {
        return moduleManager;
    }

    public ServiceRegistry services() {
        return serviceRegistry;
    }

    public PlayerRegistry players() {
        return playerRegistry;
    }

    public DataRegistry data() {
        return dataRegistry;
    }

    public ConfigSystem config() {
        return configSystem;
    }

}
