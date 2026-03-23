package vn.aoi.onii.classsystem;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import vn.aoi.onii.player.PlayerData;
import vn.aoi.onii.player.PlayerManager;

import java.util.UUID;

public class ClassManager {

    private final PlayerManager playerManager;
    private final ClassRegistry registry;

    public ClassManager(PlayerManager pm, ClassRegistry registry) {
        this.playerManager = pm;
        this.registry = registry;
    }

    public void setClass(Player player, String id) {
        PlayerData data = playerManager.get(player);

        if (data == null) return;

        String old = data.getPlayerClass();

        if (old != null) {
            PlayerClass oldClass = registry.get(old);
            if (oldClass != null) {
                oldClass.onRemove(new ClassContext(player, data));
            }
        }

        PlayerClass newClass = registry.get(id);
        if (newClass == null) return;

        data.setPlayerClass(id);

        newClass.onSelect(new ClassContext(player, data));
    }

    public PlayerClass getClass(UUID uuid) {
        Player player = Bukkit.getPlayer(uuid);
        if (player == null) return null;

        PlayerData data = playerManager.get(player);
        if (data == null) return null;

        return registry.get(data.getPlayerClass());
    }
}
 
