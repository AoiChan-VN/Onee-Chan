package vn.aoi.onii.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import vn.aoi.onii.config.ConfigManager;
import vn.aoi.onii.service.CultivationService;
import vn.aoi.onii.manager.MobManager;

public class MobKillListener implements Listener {

    private final MobManager mobManager;
    private final CultivationService cultivationService;
    private final ConfigManager config;

    public MobKillListener(MobManager mobManager,
                           CultivationService cultivationService,
                           ConfigManager config) {
        this.mobManager = mobManager;
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
