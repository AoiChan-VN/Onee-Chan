package vn.aoi.onii.cultivation;

import org.bukkit.entity.Player;

import vn.aoi.onii.Main;
import vn.aoi.onii.player.PlayerData;

import java.util.Map;

public class CultivationService {

    private final Main plugin;

    public CultivationService(Main plugin) {
        this.plugin = plugin;
    }

    public void addExp(Player player, int amount) {
        PlayerData data = plugin.getPlayerManager().get(player);
        if (data == null) return;

        data.addExp(amount);
        processLevelUp(player, data);
    }

    private void processLevelUp(Player player, PlayerData data) {

        while (true) {
            Realm realm = plugin.getRealmManager().getRealm(data.getRealm());
            if (realm == null) return;

            int currentLevel = data.getLevel();

            // MAX LEVEL → BREAKTHROUGH
            if (currentLevel >= realm.getMaxLevel()) {
                handleBreakthrough(player, data, realm);
                return;
            }

            int requiredExp = realm.getRequiredExp(currentLevel);

            if (data.getExp() < requiredExp) return;

            // LEVEL UP
            data.setExp(data.getExp() - requiredExp);
            data.setLevel(currentLevel + 1);

            player.sendMessage("§a⬆ Đột phá tầng " + data.getLevel());

        }
    }

    private void handleBreakthrough(Player player, PlayerData data, Realm realm) {

        if (realm.isThienKiep()) {
            player.sendMessage("§c⚡ Thiên kiếp giáng xuống!");
            ThunderTribulation.start(player);
            return;
        }

        String next = realm.getNextRank();
        if (next == null) return;

        data.setRealm(next);
        data.setLevel(1);
        data.setExp(0);

        player.sendMessage("§6✨ Đột phá cảnh giới: " + next);
    }
}
