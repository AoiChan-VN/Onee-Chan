package vn.aoi.onii.listener;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import vn.aoi.onii.manager.CultivationService;

public class MobKillListener implements Listener {

    private final CultivationService cultivationService;
    private final FileConfiguration config;

    public MobKillListener(CultivationService cultivationService, FileConfiguration config) {
        this.cultivationService = cultivationService;
        this.config = config;
    }

    @EventHandler
    public void onKill(EntityDeathEvent event) {
        Player killer = event.getEntity().getKiller();
        if (killer == null) return;

        EntityType type = event.getEntityType();

        double exp = config.getDouble("mobs-exp." + type.name(), 0);

        if (exp <= 0) return;

        cultivationService.addExp(killer, exp);
    }
}
