package vn.aoi.onii.task;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
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
    
    private final int totalStrikes;
    private final double damage;
    private int strikesDone = 0;

    public TribulationTask(Player player, 
                          PlayerManager playerManager, 
                          RealmManager realmManager, 
                          Realm realmData) {
        this.player = player;
        this.playerManager = playerManager;
        this.realmManager = realmManager;
        this.totalStrikes = realmData.getStrikes();
        this.damage = realmData.getDamage();

        // Hiệu ứng bắt đầu: Trời tối cục bộ
        player.setPlayerTime(18000, false);
        player.sendMessage("§8[!] Thiên địa biến sắc, lôi kiếp đang tụ lại...");
    }

    @Override
    public void run() {
        if (!player.isOnline() || player.isDead()) {
            stopTask();
            return;
        }

        if (strikesDone >= totalStrikes) {
            success();
            stopTask();
            return;
        }

        strikesDone++;
        Location loc = player.getLocation();

        // Sét đánh thẳng vào vị trí cao nhất trên đầu player
        Location strikeLoc = loc.clone();
        strikeLoc.setY(loc.getWorld().getHighestBlockYAt(loc));
        player.getWorld().strikeLightning(strikeLoc);

        // Gây sát thương tăng dần theo số đợt
        player.damage(damage + (strikesDone * 1.5)); 
        
        // Âm thanh và thông báo
        player.playSound(loc, org.bukkit.Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 1f, 0.5f + (strikesDone * 0.1f));
        player.sendMessage("§c⚡ Đạo lôi kiếp thứ " + strikesDone + "/" + totalStrikes + "!");
    }

    private void success() {
        Cultivator c = playerManager.get(player.getUniqueId());
        if (c == null) return;

        String old = c.getRealm();
        Realm realmData = realmManager.getRealm(old);
        String next = realmData.getNextRank();

        c.setRealm(next);
        c.setLevel(1);
        c.setExp(0);

        Bukkit.getPluginManager().callEvent(new PlayerLevelUpEvent(player, old, next, 1));
        player.sendMessage("§b⚡ Chúc mừng! Bạn đã vượt qua " + totalStrikes + " đạo lôi kiếp, đột phá lên " + next);
    }

    private void stopTask() {
        if (player.isOnline()) player.resetPlayerTime();
        this.cancel();
    }
}
