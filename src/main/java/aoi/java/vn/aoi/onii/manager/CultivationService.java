package vn.aoi.onii.manager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import vn.aoi.onii.api.PlayerExpGainEvent;
import vn.aoi.onii.api.PlayerLevelUpEvent;
import vn.aoi.onii.model.Cultivator;
import vn.aoi.onii.model.Realm;

public class CultivationService {

    private final PlayerManager playerManager;
    private final RealmManager realmManager;

    public CultivationService(PlayerManager playerManager, RealmManager realmManager) {
        this.playerManager = playerManager;
        this.realmManager = realmManager;
    }

    // ➕ ADD EXP
    public void addExp(Cultivator cultivator, double amount) {
        if (amount <= 0) return;

        cultivator.setExp(cultivator.getExp() + amount);

        checkLevelUp(cultivator);
    }

    // 🔼 LEVEL UP LOGIC
    private void checkLevelUp(Cultivator cultivator) {
        Realm realm = realmManager.getRealm(cultivator.getRealm());

        if (realm == null) return;

        while (true) {
            int level = cultivator.getLevel();

            // MAX → RANK UP
            if (level >= realm.getMaxLevel()) {
                if (realm.getNextRank() != null) {
                    handleRankUp(cultivator, realm);
                }
                break;
            }

            Realm.LevelData data = realm.getLevels().get(level);

            if (data == null) break;

            if (cultivator.getExp() >= data.getExpRequired()) {
                cultivator.setExp(cultivator.getExp() - data.getExpRequired());
                cultivator.setLevel(level + 1);
            } else {
                break;
            }
        }
    }

    // 🌩️ RANK UP
    private void handleRankUp(Cultivator cultivator, Realm realm) {
        if (realm.isTribulation()) {
            // TODO: trigger TribulationTask
            return;
        }

        cultivator.setRealm(realm.getNextRank());
        cultivator.setLevel(1);
        cultivator.setExp(0);
    }
}
