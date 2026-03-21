// File: src/main/java/vn/aoi/onii/core/database/sqlite/SQLiteManager.java
package vn.aoi.onii.core.database.sqlite;

import vn.aoi.onii.core.database.DatabaseManager;

import java.sql.Connection;

public class SQLiteManager implements DatabaseManager {

    private Connection connection;

    @Override
    public void connect() {
        //【❅】 Setup SQLite connection
    }

    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public void shutdown() {
        //【❅】 Close connection
    }
} 
