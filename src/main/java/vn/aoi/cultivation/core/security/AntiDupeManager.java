package vn.aoi.cultivation.core.security;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class AntiDupeManager {

    /**
     * Snapshot inventory theo UUID
     */
    private final Map<UUID, ItemStack[]> inventorySnapshots = new ConcurrentHashMap<>();

    /**
     * Session GUI đang hoạt động
     */
    private final Map<UUID, Boolean> activeSessions = new ConcurrentHashMap<>();

    /**
     * Bắt đầu session
     */
    public void startSession(Player player, Inventory inventory) {

        UUID uuid = player.getUniqueId();

        inventorySnapshots.put(
                uuid,
                deepCloneContents(inventory.getContents())
        );

        activeSessions.put(uuid, Boolean.TRUE);
    }

    /**
     * Kết thúc session
     */
    public void endSession(Player player) {

        UUID uuid = player.getUniqueId();

        inventorySnapshots.remove(uuid);
        activeSessions.remove(uuid);
    }

    /**
     * Kiểm tra session
     */
    public boolean isInSession(Player player) {
        return activeSessions.containsKey(player.getUniqueId());
    }

    /**
     * Phát hiện chỉnh sửa inventory bất thường
     */
    public boolean detectTamper(Player player, Inventory inventory) {

        UUID uuid = player.getUniqueId();

        ItemStack[] snapshot = inventorySnapshots.get(uuid);

        if (snapshot == null) {
            return false;
        }

        ItemStack[] current = inventory.getContents();

        if (snapshot.length != current.length) {
            return true;
        }

        for (int slot = 0; slot < snapshot.length; slot++) {

            ItemStack original = snapshot[slot];
            ItemStack now = current[slot];

            if (!isSameItem(original, now)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Rollback inventory
     */
    public void rollback(Player player, Inventory inventory) {

        UUID uuid = player.getUniqueId();

        ItemStack[] snapshot = inventorySnapshots.get(uuid);

        if (snapshot == null) {
            return;
        }

        inventory.setContents(
                deepCloneContents(snapshot)
        );
    }

    /**
     * Cleanup player
     */
    public void clear(Player player) {

        UUID uuid = player.getUniqueId();

        inventorySnapshots.remove(uuid);
        activeSessions.remove(uuid);
    }

    /**
     * Shutdown cleanup
     */
    public void clearAll() {

        inventorySnapshots.clear();
        activeSessions.clear();
    }

    /**
     * Deep clone ItemStack[]
     */
    private ItemStack[] deepCloneContents(ItemStack[] source) {

        ItemStack[] cloned = new ItemStack[source.length];

        for (int i = 0; i < source.length; i++) {

            ItemStack item = source[i];

            cloned[i] = item == null
                    ? null
                    : item.clone();
        }

        return cloned;
    }

    /**
     * So sánh item an toàn
     */
    private boolean isSameItem(ItemStack first,
                               ItemStack second) {

        if (first == null && second == null) {
            return true;
        }

        if (first == null || second == null) {
            return false;
        }

        if (!first.isSimilar(second)) {
            return false;
        }

        return first.getAmount() == second.getAmount();
    }
}
