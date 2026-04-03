package vn.aoi.onii.task;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import vn.aoi.onii.manager.CultivationService;
import vn.aoi.onii.manager.PlayerManager;
import vn.aoi.onii.model.Cultivator;

import java.util.Random;

public class TribulationTask extends BukkitRunnable {

    private final Player player;
    private final PlayerManager playerManager;
    private final CultivationService cultivationService;
    private final int duration;

    private int time = 0;
    private final Random random = new Random();

    public TribulationTask(Player player,
                           PlayerManager playerManager,
                           int duration,
                           CultivationService cultivationService) {

        this.player = player;
        this.playerManager = playerManager;
        this.duration = duration;
        this.cultivationService = cultivationService;
    }

    @Override
    public void run() {

        if (!player.isOnline() || player.isDead()) {
            fail();
            cancel();
            return;
        }

        if (time >= duration) {
            success();
            cancel();
            return;
        }

        Location loc = player.getLocation().clone().add(
                random.nextInt(6) - 3,
                0,
                random.nextInt(6) - 3
        );

        player.getWorld().strikeLightning(loc);

        time++;
    }

    private void fail() {
        Cultivator c = playerManager.get(player.getUniqueId());
        if (c != null) {
            c.setExp(0);
        }

        player.sendMessage("§4Bạn đã thất bại thiên kiếp!");
    }

    private void success() {
        Cultivator c = playerManager.get(player.getUniqueId());
        if (c == null) return;

        String next = cultivationService
                .getRealmManager()
                .getRealm(c.getRealm())
                .getNextRank();

        c.setRealm(next);
        c.setLevel(1);
        c.setExp(0);

        player.sendMessage("§b⚡ Bạn đã vượt qua thiên kiếp!");
    }
}
