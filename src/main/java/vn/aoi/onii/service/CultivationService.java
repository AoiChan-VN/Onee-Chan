package vn.aoi.onii.service;

import org.bukkit.*;
import org.bukkit.entity.Player;
import vn.aoi.onii.util.RankUtil;

import java.util.Random;

public class CultivationService {

    public static void handleTribulation(Player player) {
        World world = player.getWorld();
        Location loc = player.getLocation();
        Random rand = new Random();

        int duration = 30 * 20;

        Bukkit.getScheduler().runTaskTimer(
            Bukkit.getPluginManager().getPlugin("AoiChan"),
            new Runnable() {
                int time = 0;

                @Override
                public void run() {
                    if (time >= duration || player.isDead()) {
                        return;
                    }

                    Location strike = loc.clone().add(
                        rand.nextInt(10) - 5,
                        0,
                        rand.nextInt(10) - 5
                    );

                    world.strikeLightning(strike);
                    time += 20;
                }
            },
            0L, 20L
        );
    }
} 
