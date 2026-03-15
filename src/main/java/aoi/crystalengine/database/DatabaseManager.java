package com.aoi.crystalengine.database;

import java.sql.Connection;
import java.sql.DriverManager;

/*
#【!】Code:
MySQL connection manager
*/

public class DatabaseManager {

    private Connection connection;

    public void connect(String host, String db, String user, String pass) {

        try {

            connection = DriverManager.getConnection(
                "jdbc:mysql://" + host + "/" + db,
                user,
                pass
            );

        } catch (Exception e) {

            e.printStackTrace();
        }

    }

    public Connection getConnection() {

        return connection;
    }

} 
