package aoichan.autosave;

import aoichan.service.player.PlayerSession;
import aoichan.thread.AsyncPool;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class SaveQueue {

    private static final Queue<PlayerSession> queue = new ConcurrentLinkedQueue<>();

    public static void add(PlayerSession session) {
        queue.add(session);
    }

    public static void process() {
        AsyncPool.run(() -> {
            PlayerSession session;
            while ((session = queue.poll()) != null) {
                session.clean();
            }
        });
    }
}
 
