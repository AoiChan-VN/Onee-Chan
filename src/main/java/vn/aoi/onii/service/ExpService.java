package vn.aoi.onii.service;

import org.bukkit.entity.Player;

import vn.aoi.onii.config.ConfigManager;
import vn.aoi.onii.manager.PlayerManager;
import vn.aoi.onii.manager.RealmManager;
import vn.aoi.onii.model.Cultivator;
import vn.aoi.onii.model.Realm;
import vn.aoi.onii.task.TribulationTask;

public class ExpService {

    private final PlayerManager playerManager;
    private final RealmManager realmManager;
    private final ConfigManager config;

    public ExpService(PlayerManager playerManager, RealmManager realmManager, 
                      ConfigManager config) {
        this.playerManager = playerManager;
        this.realmManager = realmManager;
        this.config = config;
    }

    public void addExp(Player player, double amount) {
        Cultivator cultivator = playerManager.get(player.getUniqueId());
        if (cultivator == null) return;

        Realm realm = realmManager.getRealm(cultivator.getRealm());
        if (realm == null) return;

        double exp = cultivator.getExp() + amount;
        int level = cultivator.getLevel();

        while (true) {
            Realm.LevelData data = realm.getLevels().get(level);
            if (data == null) break;

            double required = data.getExpRequired();
            if (exp < required) break;

            // Kiểm tra mốc đột phá cảnh giới
            if (level >= realm.getMaxLevel()) {
                handleRealmUp(player, cultivator, realm);
                return; // Dừng cộng EXP để đợi độ kiếp
            }

            exp -= required;
            level++;
            player.sendMessage(config.getMessage("level.up", "%level%", String.valueOf(level)));
        }

        cultivator.setLevel(level);
        cultivator.setExp(exp);
    }

    private void handleRealmUp(Player player, Cultivator cultivator, Realm realm) {
        if (realm.getNextRank() == null) return;

        if (realm.isTribulation()) {
            player.sendMessage(config.getMessage("tribulation.start"));
            // Kích hoạt lôi kiếp
            new TribulationTask(player, playerManager, realmManager, realm)
                .runTaskTimer(AoiPlugin.get(), 0L, realm.getInterval());
        } else {
            // Lên cấp trực tiếp
            String next = realm.getNextRank();
            cultivator.setRealm(next);
            cultivator.setLevel(1);
            cultivator.setExp(0);
            player.sendMessage(config.getMessage("level.realm-up", "%realm%", next));
        }
    }
}
