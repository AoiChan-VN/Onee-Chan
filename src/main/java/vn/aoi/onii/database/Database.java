package vn.aoi.onii.database;

import vn.aoi.onii.Main;
import org.bukkit.entity.Player;

import java.sql.*;
import java.util.UUID;

public class Database {

    private final Main plugin;
    private Connection connection;

    public Database(Main plugin) {
        this.plugin = plugin;
    }

    public synchronized void connect() {
        try {
            if (connection != null && !connection.isClosed()) return;

            connection = DriverManager.getConnection("jdbc:sqlite:plugins/Onii/data.db");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void createTable() {
        try (PreparedStatement ps = connection.prepareStatement(
                "CREATE TABLE IF NOT EXISTS players (" +
                "uuid TEXT PRIMARY KEY," +
                "name TEXT," +
                "realm TEXT," +
                "stage TEXT," +
                "sect TEXT," +
                "technique TEXT," +
                "level INTEGER," +
                "realm TEXT)"
        )) {
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public PlayerData load(Player player) {
        UUID uuid = player.getUniqueId();

        try (PreparedStatement ps = connection.prepareStatement(
                "SELECT * FROM players WHERE uuid=?"
        )) {
            ps.setString(1, uuid.toString());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new PlayerData(
                        rs.getInt("level"),
                        rs.getString("realm")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new PlayerData(1, "Mortal");
    }

    public void save(Player player, PlayerData data) {
        save(player.getUniqueId(), data);
    }

    public void save(UUID uuid, PlayerData data) {
        try (PreparedStatement ps = connection.prepareStatement(
                "REPLACE INTO players(uuid, level, realm) VALUES(?,?,?)"
        )) {
            ps.setString(1, uuid.toString());
            ps.setInt(2, data.getLevel());
            ps.setString(3, data.getRealm());
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            if (connection != null) connection.close();
        } catch (Exception ignored) {}
    }
}
