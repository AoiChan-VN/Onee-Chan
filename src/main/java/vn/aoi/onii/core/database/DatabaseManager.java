// File: src/main/java/vn/aoi/onii/core/database/DatabaseManager.java
package vn.aoi.onii.core.database;

import java.sql.Connection;

public interface DatabaseManager {
    //【❅】 Connect to database
    void connect();

    //【❅】 Get SQL connection
    Connection getConnection();

    //【❅】 Shutdown database
    void shutdown();
} 
