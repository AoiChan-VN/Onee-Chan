package vn.aoi.onii.cultivation;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import vn.aoi.onii.Main;

import java.util.Random;

public class ThunderTribulation {

    public static void start(Player player) {
        new BukkitRunnable() {

            int ticks = 0;
            final Random random = new Random();

            @Override
            public void run() {
                if (ticks >= 600) {
                    if (!player.isDead()) {
                        player.sendMessage("§a✔ Bạn đã vượt qua Thiên Kiếp!");
                    }
                    cancel();
                    return;
                }

                Location loc = player.getLocation().clone().add(
                        random.nextInt(6) - 3,
                        0,
                        random.nextInt(6) - 3
                );

                player.getWorld().strikeLightning(loc);
                ticks += 20;
            }

        }.runTaskTimer(Main.getInstance(), 0, 20);
    }
} 
