package vn.aoi.onii.manager;

import org.bukkit.entity.Player;
import vn.aoi.onii.data.PlayerData;
import vn.aoi.onii.manager.model.Realm;
import vn.aoi.onii.manager.model.LevelInfo;
import vn.aoi.onii.task.ThunderTask;

public class LevelManager {

    public static void checkLevelUp(Player player, PlayerData data) {

        while (true) {
            Realm realm = RealmManager.get(data.getRealm());
            if (realm == null) return;

            int level = data.getLevel();

            // Max level → chuyển cảnh giới
            if (level >= realm.getMaxLevel()) {

                if (realm.getNext() == null) return;

                if (realm.isTribulation()) {
                    new ThunderTask(player).start();
                    return;
                }

                data.setRealm(realm.getNext());
                data.setLevel(1);
                player.sendMessage("§dĐột phá cảnh giới: §e" + realm.getNext());
                continue;
            }

            LevelInfo info = realm.getLevels().get(level);
            if (info == null) return;

            if (data.getExp() < info.getExp()) return;

            data.addExp(-info.getExp());
            data.setLevel(level + 1);

            player.sendMessage("§aĐột phá tầng " + (level + 1));
        }
    }
}
