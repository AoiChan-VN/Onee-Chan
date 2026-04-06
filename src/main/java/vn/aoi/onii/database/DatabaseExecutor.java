package vn.aoi.onii.database;

import java.util.concurrent.*;
import java.util.function.Supplier;

public class DatabaseExecutor {

    private final ExecutorService executor = Executors.newFixedThreadPool(4);

    public void execute(Runnable task) {
        executor.execute(task);
    }

    public <T> CompletableFuture<T> supply(Supplier<T> task) {
        return CompletableFuture.supplyAsync(task, executor);
    }

    public void shutdown() {
        executor.shutdown();
    }
} 
