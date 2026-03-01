package aoichan.crystal.storage;

import aoichan.crystal.AoiMain;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DatabasePool {

    private final HikariDataSource dataSource;

    public DatabasePool(AoiMain plugin) {
        HikariConfig config = new HikariConfig();
        String type = plugin.getConfig().getString("storage.type", "SQLITE");

        if ("MYSQL".equalsIgnoreCase(type)) {
            String host = plugin.getConfig().getString("mysql.host", "localhost");
            String port = plugin.getConfig().getString("mysql.port", "3306");
            String db = plugin.getConfig().getString("mysql.database", "gems");
            String user = plugin.getConfig().getString("mysql.username", "root");
            String pass = plugin.getConfig().getString("mysql.password", "");
            config.setJdbcUrl("jdbc:mysql://" + host + ":" + port + "/" + db + "?useSSL=false&serverTimezone=UTC");
            config.setUsername(user);
            config.setPassword(pass);
        } else {
            config.setJdbcUrl("jdbc:sqlite:" + plugin.getDataFolder() + "/data.db");
        }

        config.setMaximumPoolSize(10);
        config.setPoolName("GemsPool");
        this.dataSource = new HikariDataSource(config);
    }

    public HikariDataSource getDataSource() {
        return dataSource;
    }

    public void shutdown() {
        if (dataSource != null && !dataSource.isClosed()) dataSource.close();
    }
}
