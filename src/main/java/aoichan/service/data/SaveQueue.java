package aoichan.service.data;

import aoichan.thread.AsyncPool;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class SaveQueue {

    private final Queue<Runnable> queue = new ConcurrentLinkedQueue<>();

    public void add(Runnable saveTask) {
        queue.add(saveTask);
    }

    public void flushBatch() {
        List<Runnable> batch = new ArrayList<>();

        Runnable r;
        while ((r = queue.poll()) != null) {
            batch.add(r);
        }

        if (batch.isEmpty()) return;

        AsyncPool.run(() -> batch.forEach(Runnable::run));
    }
}
