// File: src/main/java/vn/aoi/onii/core/database/mysql/MySQLManager.java
package vn.aoi.onii.core.database.mysql;

import vn.aoi.onii.core.database.DatabaseManager;

import java.sql.Connection;

public class MySQLManager implements DatabaseManager {

    private Connection connection;

    @Override
    public void connect() {
        //【❅】 Setup MySQL connection
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
