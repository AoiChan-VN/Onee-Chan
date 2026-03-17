package aoi.aoichan.scheduler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Thread pool dùng cho async tasks
 */
public class EngineThreadPool {

    private final ExecutorService executor;

    public EngineThreadPool() {
        this.executor = Executors.newFixedThreadPool(
                Runtime.getRuntime().availableProcessors()
        );
    }

    public void execute(Runnable task) {
        executor.execute(task);
    }

    public void shutdown() {
        executor.shutdown();
    }
} 
