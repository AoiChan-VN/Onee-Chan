package vn.aoi.onii.database;

import java.io.File;
import java.sql.*;
import org.bukkit.plugin.java.JavaPlugin;

public class Database {

    private Connection connection;
    private final JavaPlugin plugin;

    public Database(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void connect() {
        try {
            File file = new File(plugin.getDataFolder(), "data.db");
            if (!file.exists()) plugin.getDataFolder().mkdirs();

            connection = DriverManager.getConnection("jdbc:sqlite:" + file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createTable() {
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("""
            CREATE TABLE IF NOT EXISTS players (
                uuid TEXT PRIMARY KEY,
                name TEXT,
                realm TEXT,
                stage TEXT,
                sect TEXT,
                technique TEXT
            )
            """);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void close() {
        try {
            if (connection != null) connection.close();
        } catch (Exception ignored) {}
    }
}
