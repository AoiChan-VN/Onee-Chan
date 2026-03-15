package aoi.aoichan.core;

import aoi.aoichan.AoiMain;

/*
 * Bootstrap hệ thống CrystalEngine
 */

public class EngineBootstrap {

    private final AoiMain plugin;

    public EngineBootstrap(AoiMain plugin) {
        this.plugin = plugin;
    }

    public void start() {

        // 【!】Code: Bắt đầu load các hệ thống core
        plugin.getEngineLogger().info("Đang load Core Systems...");

        // Sau này sẽ load:
        // - ModuleManager
        // - EventBus
        // - Database
        // - API

        plugin.getEngineLogger().info("Core Systems đã load xong.");
    }

    public void shutdown() {

        // 【!】Code: Tắt các service
        plugin.getEngineLogger().info("Đang shutdown Core Systems...");
    }
} 
