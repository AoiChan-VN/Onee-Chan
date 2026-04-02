package vn.aoi.onii.listener;

import org.bukkit.event.*;
import org.bukkit.event.player.PlayerJoinEvent;
import vn.aoi.onii.Main;
import vn.aoi.onii.data.PlayerData;
import vn.aoi.onii.manager.PlayerManager;

import java.sql.*;

public class JoinListener implements Listener {

    @EventHandler
    public void join(PlayerJoinEvent e) {

        var uuid = e.getPlayer().getUniqueId();

        try {
            var ps = Main.getInstance().getDatabase().getConnection()
                    .prepareStatement("SELECT * FROM players WHERE uuid=?");

            ps.setString(1, uuid.toString());
            var rs = ps.executeQuery();

            if (rs.next()) {
                PlayerManager.set(new PlayerData(
                        uuid,
                        rs.getString("realm"),
                        rs.getInt("level"),
                        rs.getInt("exp")
                ));
            } else {
                PlayerManager.set(new PlayerData(uuid, "Phàm nhân", 1, 0));
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
} 
