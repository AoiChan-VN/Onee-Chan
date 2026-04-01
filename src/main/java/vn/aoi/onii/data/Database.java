package vn.aoi.onii.data;

import vn.aoi.onii.AoiMain;
import java.sql.*;

public class Database {
    private final AoiMain plugin;
    private Connection conn;

    public Database(AoiMain plugin) { this.plugin = plugin; }

    public void init() {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:" + plugin.getDataFolder() + "/data.db");
            try (Statement st = conn.createStatement()) {
                st.execute("CREATE TABLE IF NOT EXISTS players(uuid TEXT PRIMARY KEY,rank TEXT,level INT,exp INT);");
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    public Connection get() { return conn; }
    public void close() { try { if (conn!=null) conn.close(); } catch(Exception ignored){} }
}
