package vn.aoi.onii.listener;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import vn.aoi.onii.service.CultivationService;

public class MobKillListener implements Listener {

    private final CultivationService cultivationService;
    private final FileConfiguration config;

    public MobKillListener(CultivationService cultivationService, FileConfiguration config) {
        this.cultivationService = cultivationService;
        this.config = config;
    }

    @EventHandler
    public void onKill(EntityDeathEvent event) {
        if (!(event.getEntity().getKiller() instanceof Player player)) return;

        double exp = mobManager.getExp(event.getEntityType());
        if (exp <= 0) return;

        cultivationService.addExp(player, exp);

        player.sendMessage(config.getMessage("exp.gain",
                "%amount%", String.valueOf(exp)));
    }
}
