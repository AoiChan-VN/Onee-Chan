package vn.aoi.onii.data;

import vn.aoi.onii.AoiMain;

import java.sql.*;

public class Database {

    private final AoiMain plugin;
    private Connection connection;

    public Database(AoiMain plugin) {
        this.plugin = plugin;
    }

    public void init() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + plugin.getDataFolder() + "/data.db");

            try (Statement stmt = connection.createStatement()) {
                stmt.execute("""
                        CREATE TABLE IF NOT EXISTS players (
                            uuid TEXT PRIMARY KEY,
                            rank TEXT,
                            level INTEGER,
                            exp INTEGER
                        );
                        """);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void close() {
        try {
            if (connection != null) connection.close();
        } catch (SQLException ignored) {}
    }
}
