package aoichan.engine;

import aoichan.service.ServiceManager;
import aoichan.thread.AsyncPool;
import aoichan.util.Logger;
import org.bukkit.plugin.java.JavaPlugin;

public class Engine {

    private static EngineState state;
    private static ServiceManager services;
    private static JavaPlugin plugin;

    public static void start(JavaPlugin pl) {
        plugin = pl;

        setState(EngineState.LOAD);
        AsyncPool.init();

        services = new ServiceManager();
        services.init();

        setState(EngineState.ENABLE);

        setState(EngineState.ACTIVE);
        Logger.info("Engine started");
    }

    public static void shutdown() {
        setState(EngineState.DISABLE);

        services.shutdown();
        AsyncPool.shutdown();

        Logger.info("Engine stopped");
    }

    private static void setState(EngineState newState) {
        state = newState;
    }

    public static EngineState state() {
        return state;
    }

    public static JavaPlugin plugin() {
        return plugin;
    }

    public static ServiceManager services() {
        return services;
    }
}
