package vn.aoi.onii.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {

    private final FileConfiguration config;

    private HikariDataSource hikari;
    private Connection sqliteConnection;

    public DatabaseManager(FileConfiguration config) {
        this.config = config;
    }

    public void connect(File dataFolder) {

        String type = config.getString("type", "SQLITE").toUpperCase();

        try {
            if (type.equals("MYSQL")) {
                setupMySQL();
            } else {
                setupSQLite(dataFolder);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ================= MYSQL =================

    private void setupMySQL() {

        HikariConfig cfg = new HikariConfig();

        String host = config.getString("mysql.host");
        int port = config.getInt("mysql.port");
        String db = config.getString("mysql.database");

        cfg.setJdbcUrl("jdbc:mysql://" + host + ":" + port + "/" + db + "?useSSL=false");

        cfg.setUsername(config.getString("mysql.username"));
        cfg.setPassword(config.getString("mysql.password"));

        cfg.setMaximumPoolSize(config.getInt("mysql.pool-size", 10));

        hikari = new HikariDataSource(cfg);
    }

    // ================= SQLITE =================

    private void setupSQLite(File dataFolder) throws SQLException {

        File file = new File(dataFolder, config.getString("sqlite.file"));

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (Exception ignored) {}
        }

        sqliteConnection = DriverManager.getConnection("jdbc:sqlite:" + file.getAbsolutePath());
    }

    // ================= GET CONNECTION =================

    public Connection getConnection() throws SQLException {

        if (hikari != null) {
            return hikari.getConnection();
        }

        if (sqliteConnection != null && !sqliteConnection.isClosed()) {
            return sqliteConnection;
        }

        throw new SQLException("Database not connected!");
    }

    public void close() {
        try {
            if (hikari != null) hikari.close();
            if (sqliteConnection != null) sqliteConnection.close();
        } catch (Exception ignored) {}
    }
}
