package aoichan.thread;

import java.util.concurrent.*;

public class AsyncPool {

    private static ExecutorService pool;

    public static void init() {
        pool = Executors.newWorkStealingPool();
    }

    public static void run(Runnable task) {
        pool.submit(task);
    }

    public static void shutdown() {
        pool.shutdown();
    }
}
