package aoichan.service.data;

import aoichan.thread.AsyncPool;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class SaveQueue {

    private final Queue<Runnable> queue = new ConcurrentLinkedQueue<>();

    public void add(Runnable saveTask) {
        queue.add(saveTask);
    }

    public void flush() {
        AsyncPool.run(() -> {
            Runnable r;
            while ((r = queue.poll()) != null) {
                r.run();
            }
        });
    }
} 
