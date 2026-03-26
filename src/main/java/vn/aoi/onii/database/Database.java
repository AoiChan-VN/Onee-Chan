package vn.aoi.onii.database;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.sql.*;

public class Database {

    private Connection conn;
    private final JavaPlugin plugin;

    public Database(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void connect() {
        try {
            File file = new File(plugin.getDataFolder(), "data.db");
            if (!file.exists()) plugin.getDataFolder().mkdirs();

            conn = DriverManager.getConnection("jdbc:sqlite:" + file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createTable() {
        try {
            conn.createStatement().executeUpdate("""
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

    public Connection get() {
        return conn;
    }
}
