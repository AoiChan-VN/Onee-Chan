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

    // ✅ ADD EXP (FULL)
    public void addExp(Player player, double amount) {
        if (amount <= 0 || amount > 1000) return; // anti exploit

        Cultivator cultivator = playerManager.get(player.getUniqueId());
        if (cultivator == null) return;

        // 🔔 CALL EVENT
        PlayerExpGainEvent event = new PlayerExpGainEvent(player, amount);
        Bukkit.getPluginManager().callEvent(event);

        double finalAmount = event.getAmount();
        if (finalAmount <= 0) return;

        cultivator.setExp(cultivator.getExp() + finalAmount);

        checkLevelUp(player, cultivator);
    }

    // 🔼 LEVEL UP (FULL)
    private void checkLevelUp(Player player, Cultivator cultivator) {
        Realm realm = realmManager.getRealm(cultivator.getRealm());
        if (realm == null) return;

        while (true) {
            int level = cultivator.getLevel();

            if (level >= realm.getMaxLevel()) {
                if (realm.getNextRank() != null) {
                    handleRankUp(player, cultivator, realm);
                }
                break;
            }

            Realm.LevelData data = realm.getLevels().get(level);
            if (data == null) break;

            if (cultivator.getExp() >= data.getExpRequired()) {
                cultivator.setExp(cultivator.getExp() - data.getExpRequired());
                cultivator.setLevel(level + 1);

                // 🔔 LEVEL EVENT
                Bukkit.getPluginManager().callEvent(
                        new PlayerLevelUpEvent(
                                player,
                                cultivator.getRealm(),
                                cultivator.getRealm(),
                                cultivator.getLevel()
                        )
                );

            } else break;
        }
    }

    // 🌩️ RANK UP (FULL)
    private void handleRankUp(Player player, Cultivator cultivator, Realm realm) {

        if (realm.isTribulation()) {
            // TODO: hook TribulationTask ở Phase 3
            return;
        }

        String oldRealm = cultivator.getRealm();
        String newRealm = realm.getNextRank();

        cultivator.setRealm(newRealm);
        cultivator.setLevel(1);
        cultivator.setExp(0);

        Bukkit.getPluginManager().callEvent(
                new PlayerLevelUpEvent(player, oldRealm, newRealm, 1)
        );
    }
}
