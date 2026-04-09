package vn.aoi.onii.task;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import vn.aoi.onii.api.PlayerLevelUpEvent;
import vn.aoi.onii.service.CultivationService;
import vn.aoi.onii.manager.PlayerManager;
import vn.aoi.onii.model.Cultivator;

import java.util.Random;

public class TribulationTask extends BukkitRunnable {

    private final Player player;
    private final PlayerManager playerManager;
    private final CultivationService service;
    private final int duration;

    private int time = 0;
    private final Random random = new Random();

    public TribulationTask(Player player,
                           PlayerManager playerManager,
                           int duration,
                           CultivationService service) {

        this.player = player;
        this.playerManager = playerManager;
        this.duration = duration;
        this.service = service;
    }

    @Override
    public void run() {

        if (!player.isOnline() || player.isDead()) {
            cancel();
            return;
        }

        if (time >= duration) {
            success();
            cancel();
            return;
        }

        Location loc = player.getLocation().clone().add(
                random.nextInt(7) - 3,
                0,
                random.nextInt(7) - 3
        );

        player.getWorld().strikeLightning(loc);

        time++;
    }

    private void success() {

        Cultivator c = playerManager.get(player.getUniqueId());
        if (c == null) return;

        String old = c.getRealm();
        String next = service.getRealmManager()
                .getRealm(old)
                .getNextRank();

        c.setRealm(next);
        c.setLevel(1);
        c.setExp(0);

        Bukkit.getPluginManager().callEvent(
                new PlayerLevelUpEvent(player, old, next, 1)
        );

        player.sendMessage("§b⚡ Vượt thiên kiếp!");
    }
}
