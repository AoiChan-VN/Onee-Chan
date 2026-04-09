package vn.aoi.onii.service;

import org.bukkit.entity.Player;
import vn.aoi.onii.config.ConfigManager;
import vn.aoi.onii.manager.PlayerManager;
import vn.aoi.onii.manager.RealmManager;
import vn.aoi.onii.model.Cultivator;
import vn.aoi.onii.model.Realm;

public class ExpService {

    private final PlayerManager playerManager;
    private final RealmManager realmManager;
    private final ConfigManager config;

    public ExpService(PlayerManager playerManager,
                      RealmManager realmManager,
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

        // 🔁 loop level up
        while (true) {

            Realm.LevelData data = realm.getLevels().get(level);
            if (data == null) break;

            double required = data.getExpRequired();

            if (exp < required) break;

            exp -= required;
            level++;

            // 📈 lên cấp
            player.sendMessage(config.getMessage("level.up",
                    "%level%", String.valueOf(level)));

            // 🚧 max level → chuẩn bị đột phá
            if (level > realm.getMaxLevel()) {
                level = realm.getMaxLevel();
                exp = 0;
                handleRealmUp(player, cultivator, realm);
                break;
            }
        }

        cultivator.setLevel(level);
        cultivator.setExp(exp);
    }

    private void handleRealmUp(Player player, Cultivator cultivator, Realm realm) {

        String next = realm.getNextRank();
        if (next == null) return;

        // ⚡ thiên kiếp nếu có
        if (realm.isTribulation()) {
            player.sendMessage(config.getMessage("tribulation.start"));

            // 👉 sau này anh có thể thêm lightning + damage ở đây
        }

        cultivator.setRealm(next);
        cultivator.setLevel(1);
        cultivator.setExp(0);

        player.sendMessage(config.getMessage("level.realm-up",
                "%realm%", next));
    }
}
