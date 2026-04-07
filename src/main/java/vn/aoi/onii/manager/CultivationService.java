package vn.aoi.onii.manager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import vn.aoi.onii.AoiPlugin;
import vn.aoi.onii.api.PlayerExpGainEvent;
import vn.aoi.onii.api.PlayerLevelUpEvent;
import vn.aoi.onii.config.ConfigManager;
import vn.aoi.onii.model.Cultivator;
import vn.aoi.onii.model.Realm;
import vn.aoi.onii.task.TribulationTask;

public class CultivationService {

    private final PlayerManager playerManager;
    private final RealmManager realmManager;
    private final ConfigManager config;

    public CultivationService(PlayerManager playerManager,
                              RealmManager realmManager,
                              ConfigManager config) {
        this.playerManager = playerManager;
        this.realmManager = realmManager;
        this.config = config;
    }

    public void addExp(Player player, double amount) {

        if (amount <= 0 || amount > 1000) return;

        Cultivator cultivator = playerManager.get(player.getUniqueId());
        if (cultivator == null) return;

        PlayerExpGainEvent event = new PlayerExpGainEvent(player, amount);
        Bukkit.getPluginManager().callEvent(event);

        double finalAmount = event.getAmount();
        if (finalAmount <= 0) return;

        Bukkit.getScheduler().runTask(AoiPlugin.get(), () -> {

            cultivator.setExp(cultivator.getExp() + finalAmount);

            player.sendMessage(
                    config.getMessage("exp.gain",
                            "%amount%", String.valueOf(finalAmount))
            );

            checkLevelUp(player, cultivator);
        });
    }

    private void checkLevelUp(Player player, Cultivator cultivator) {

        Realm realm = realmManager.getRealm(cultivator.getRealm());
        if (realm == null) return;

        int safety = 0;

        while (safety++ < 100) {

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

                player.sendMessage(
                        config.getMessage("level.up",
                                "%level%", String.valueOf(cultivator.getLevel()))
                );

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

    private void handleRankUp(Player player, Cultivator cultivator, Realm realm) {

        if (realm.isTribulation()) {

            player.sendMessage(config.getMessage("tribulation.start"));

            new TribulationTask(player, playerManager, realm.getDuration(), this)
                    .runTaskTimer(AoiPlugin.get(), 0L, 20L);

            return;
        }

        String oldRealm = cultivator.getRealm();
        String newRealm = realm.getNextRank();

        cultivator.setRealm(newRealm);
        cultivator.setLevel(1);
        cultivator.setExp(0);

        player.sendMessage(
                config.getMessage("level.realm-up",
                        "%realm%", newRealm)
        );

        Bukkit.getPluginManager().callEvent(
                new PlayerLevelUpEvent(player, oldRealm, newRealm, 1)
        );
    }

    public RealmManager getRealmManager() {
        return realmManager;
    }
}
