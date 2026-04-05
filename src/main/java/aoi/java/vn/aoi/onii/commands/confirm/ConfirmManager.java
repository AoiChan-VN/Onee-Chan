package vn.aoi.onii.commands.confirm;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ConfirmManager {

    private static final Map<UUID, ConfirmData> confirms = new ConcurrentHashMap<>();

    public static void request(UUID uuid, Runnable action) {
        confirms.put(uuid, new ConfirmData(action, System.currentTimeMillis()));
    }

    public static boolean confirm(UUID uuid) {
        ConfirmData data = confirms.remove(uuid);

        if (data == null) return false;

        if (System.currentTimeMillis() - data.time > 10000) {
            return false;
        }

        data.action.run();
        return true;
    }

    private static class ConfirmData {
        Runnable action;
        long time;

        ConfirmData(Runnable action, long time) {
            this.action = action;
            this.time = time;
        }
    }
} 
