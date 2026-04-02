package vn.aoi.onii.data;

import org.bukkit.Bukkit;
import vn.aoi.onii.Main;

import java.sql.PreparedStatement;

public class PlayerRepository {

    public static void saveAsync(PlayerData data) {

        Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), () -> {
            try {
                PreparedStatement ps = Main.getInstance().getDatabase().getConnection()
                        .prepareStatement("UPDATE players SET realm=?, level=?, exp=? WHERE uuid=?");

                ps.setString(1, data.getRealm());
                ps.setInt(2, data.getLevel());
                ps.setInt(3, data.getExp());
                ps.setString(4, data.getUuid().toString());

                ps.executeUpdate();

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
