package vn.aoi.onii.player;

import vn.aoi.onii.database.SQLite;

import java.sql.*;
import java.util.UUID;

public class PlayerManager {

    private final SQLite sqlite;

    public PlayerManager(SQLite sqlite) {
        this.sqlite = sqlite;
    }

    public PlayerData getPlayer(UUID uuid, String name) {
        try {
            PreparedStatement ps = sqlite.getConnection().prepareStatement(
                    "SELECT * FROM players WHERE uuid=?"
            );
            ps.setString(1, uuid.toString());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new PlayerData(
                        rs.getString("name"),
                        rs.getString("canhgioi"),
                        rs.getString("tuvi")
                );
            } else {
                createPlayer(uuid, name);
                return new PlayerData(name, "Phàm nhân", "Sơ kỳ");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void createPlayer(UUID uuid, String name) {
        try {
            PreparedStatement ps = sqlite.getConnection().prepareStatement(
                    "INSERT INTO players VALUES(?,?,?,?)"
            );
            ps.setString(1, uuid.toString());
            ps.setString(2, name);
            ps.setString(3, "Phàm nhân");
            ps.setString(4, "Sơ kỳ");
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
