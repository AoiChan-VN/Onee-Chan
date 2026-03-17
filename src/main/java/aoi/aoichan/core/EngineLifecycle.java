package aoi.aoichan.core;

import aoi.aoichan.module.ModuleManager;
import aoi.aoichan.scheduler.EngineThreadPool;
import aoi.aoichan.service.ServiceRegistry;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Điều phối toàn bộ lifecycle của engine
 */
public class EngineLifecycle {

    private final JavaPlugin plugin;

    private ServiceRegistry serviceRegistry;
    private ModuleManager moduleManager;
    private EngineThreadPool threadPool;

    public EngineLifecycle(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void start() {EngineAPI.init(this);
        // 1. Init thread pool
        this.threadPool = new EngineThreadPool();

        // 2. Init service registry
        this.serviceRegistry = new ServiceRegistry();

        // 3. Register core services
        registerCoreServices();

        // 4. Load modules
        this.moduleManager = new ModuleManager(serviceRegistry);
        this.moduleManager.loadModules();

        plugin.getLogger().info("[EngineLifecycle] All systems initialized.");
    }

    public void shutdown() {
        if (moduleManager != null) {
            moduleManager.unloadModules();
        }

        if (threadPool != null) {
            threadPool.shutdown();
        }

        plugin.getLogger().info("[EngineLifecycle] Shutdown complete.");
    }

    private void registerCoreServices() {
        serviceRegistry.register(ServiceRegistry.class, serviceRegistry);
        serviceRegistry.register(EngineThreadPool.class, threadPool);
    }

    public ServiceRegistry getServiceRegistry() {
        return serviceRegistry;
    }
} 
