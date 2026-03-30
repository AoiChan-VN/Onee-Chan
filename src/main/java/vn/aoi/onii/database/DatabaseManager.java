package vn.aoi.onii.database;

import org.bukkit.Bukkit;
import vn.aoi.onii.AoiChan;

import java.sql.*;
import java.util.UUID;

public class DatabaseManager {

    private final AoiChan plugin;
    private Connection connection;

    public DatabaseManager(AoiChan plugin) {
        this.plugin = plugin;
    }

    public void init() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" +
                    plugin.getDataFolder() + "/" +
                    plugin.getConfig().getString("database.file"));

            Statement st = connection.createStatement();
            st.executeUpdate("""
                CREATE TABLE IF NOT EXISTS players (
                    uuid TEXT PRIMARY KEY,
                    rank INTEGER,
                    phase INTEGER,
                    level INTEGER
                )
            """);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createPlayer(UUID uuid) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try (PreparedStatement ps = connection.prepareStatement(
                    "INSERT OR IGNORE INTO players VALUES(?,?,?,?)")) {

                ps.setString(1, uuid.toString());
                ps.setInt(2, 0);
                ps.setInt(3, 0);
                ps.setInt(4, 1);
                ps.executeUpdate();

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
} 
