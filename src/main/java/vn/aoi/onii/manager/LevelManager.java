package vn.aoi.onii.manager;

import org.bukkit.entity.Player;
import vn.aoi.onii.data.PlayerData;
import vn.aoi.onii.model.Realm;
import vn.aoi.onii.task.ThunderTask;

public class LevelManager {

    public static void check(Player p, PlayerData d) {

        while (true) {

            Realm r = RealmManager.get(d.getRealm());
            if (r == null) return;

            // Phàm nhân kiểu exp-required
            if (r.getLevels().isEmpty()) {
                int req = vn.aoi.onii.config.ConfigManager.realms
                        .getInt("realms." + r.getName() + ".exp-required");

                if (d.getExp() < req) return;

                d.addExp(-req);
                d.setRealm(r.getNext());
                d.setLevel(1);
                continue;
            }

            int lvl = d.getLevel();

            if (lvl >= r.getMax()) {

                if (r.getNext() == null) return;

                if (r.isTrib()) {
                    new ThunderTask(p).start();
                    return;
                }

                d.setRealm(r.getNext());
                d.setLevel(1);
                continue;
            }

            int need = r.getLevels().get(lvl).getExp();

            if (d.getExp() < need) return;

            d.addExp(-need);
            d.setLevel(lvl + 1);
        }
    }
}
