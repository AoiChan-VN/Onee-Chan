// File: scheduler/AsyncExecutor.java
package vn.aoi.onii.scheduler;

import java.util.concurrent.*;

public class AsyncExecutor {

    private final ExecutorService pool;

    public AsyncExecutor() {
        this.pool = new ThreadPoolExecutor(4, 12, 60L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(), new ThreadPoolExecutor.CallerRunsPolicy());
    }

    public void run(Runnable r) {
        pool.execute(r);
    }

    public void shutdown() {
        pool.shutdown();
    }
}
