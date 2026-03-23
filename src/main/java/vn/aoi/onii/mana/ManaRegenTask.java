package vn.aoi.onii.mana;

import org.bukkit.Bukkit;
import vn.aoi.onii.Main;
import vn.aoi.onii.player.PlayerData;
import vn.aoi.onii.player.PlayerManager;

public class ManaRegenTask {

    public ManaRegenTask(Main plugin, PlayerManager pm) {
        Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            for (PlayerData d : pm.getOnline()) {
                d.regenMana(2);
            }
        }, 40L, 40L);
    }
}
