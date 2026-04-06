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
    private DatabaseType type;

    private HikariDataSource hikari;
    private Connection sqlite;

    public DatabaseManager(FileConfiguration config) {
        this.config = config;
    }

    public void connect(File dataFolder) {

        this.type = DatabaseType.from(config.getString("type"));

        try {
            switch (type) {
                case MYSQL -> setupMySQL();
                case SQLITE -> setupSQLite(dataFolder);
            }
        } catch (Exception e) {
            throw new RuntimeException("Database init failed", e);
        }
    }

    private void setupMySQL() {

        HikariConfig cfg = new HikariConfig();

        cfg.setJdbcUrl("jdbc:mysql://" +
                config.getString("mysql.host") + ":" +
                config.getInt("mysql.port") + "/" +
                config.getString("mysql.database") +
                "?useSSL=false&autoReconnect=true");

        cfg.setUsername(config.getString("mysql.username"));
        cfg.setPassword(config.getString("mysql.password"));

        cfg.setMaximumPoolSize(config.getInt("mysql.pool-size", 10));
        cfg.setMinimumIdle(2);
        cfg.setConnectionTimeout(10000);

        hikari = new HikariDataSource(cfg);
    }

    private void setupSQLite(File dataFolder) throws SQLException {

        File file = new File(dataFolder, config.getString("sqlite.file"));

        if (!file.exists()) {
            try { file.createNewFile(); } catch (Exception ignored) {}
        }

        sqlite = DriverManager.getConnection("jdbc:sqlite:" + file.getAbsolutePath());
    }

    public Connection getConnection() throws SQLException {

        if (type == DatabaseType.MYSQL) {
            return hikari.getConnection();
        }

        if (sqlite != null && !sqlite.isClosed()) {
            return sqlite;
        }

        throw new SQLException("No DB connection");
    }

    public DatabaseType getType() {
        return type;
    }

    public void close() {
        try {
            if (hikari != null) hikari.close();
            if (sqlite != null) sqlite.close();
        } catch (Exception ignored) {}
    }
}
