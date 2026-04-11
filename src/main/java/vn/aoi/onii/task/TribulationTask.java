package vn.aoi.onii.task;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import vn.aoi.onii.api.PlayerLevelUpEvent;
import vn.aoi.onii.manager.PlayerManager;
import vn.aoi.onii.manager.RealmManager;
import vn.aoi.onii.model.Cultivator;
import vn.aoi.onii.model.Realm;

public class TribulationTask extends BukkitRunnable {

    private final Player player;
    private final PlayerManager playerManager;
    private final RealmManager realmManager;
    private final Realm realmData;
    private int strikesDone = 0;

    public TribulationTask(Player player, PlayerManager playerManager, RealmManager realmManager, Realm realmData) {
        this.player = player;
        this.playerManager = playerManager;
        this.realmManager = realmManager;
        this.realmData = realmData;
        player.setPlayerTime(18000, false);
    }

    @Override
    public void run() {
        if (!player.isOnline() || player.isDead()) {
            stopTask();
            return;
        }

        if (strikesDone >= realmData.getStrikes()) {
            success();
            stopTask();
            return;
        }

        strikesDone++;
        Location loc = player.getLocation();
        
        Location strikeLoc = loc.clone();
        strikeLoc.setY(loc.getWorld().getHighestBlockYAt(loc));
        loc.getWorld().strikeLightning(strikeLoc);

        player.damage(realmData.getDamage());
        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 40, 0));
        
        float pitch = 0.5f + (strikesDone * 0.1f);
        player.playSound(loc, Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 1f, pitch);
        player.sendMessage("§c⚡ Đạo lôi kiếp thứ " + strikesDone + "/" + realmData.getStrikes() + "!");
    }

    private void success() {
        Cultivator c = playerManager.get(player.getUniqueId());
        if (c == null) return;

        String oldRealm = c.getRealm();
        String next = realmData.getNextRank();

        c.setRealm(next);
        c.setLevel(1);
        c.setExp(0);

        Bukkit.getPluginManager().callEvent(new PlayerLevelUpEvent(player, oldRealm, next, 1));
        player.sendMessage("§b⚡ Vượt qua thiên kiếp thành công! Cảnh giới hiện tại: " + next);
        player.playSound(player.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 1f, 1f);
    }

    private void stopTask() {
        if (player.isOnline()) player.resetPlayerTime();
        this.cancel();
    }
}
