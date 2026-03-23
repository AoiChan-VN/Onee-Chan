// File: player/repository/SQLitePlayerRepository.java
package vn.aoi.onii.player.repository.impl;

import com.google.gson.Gson;
import vn.aoi.onii.database.DatabaseManager;
import vn.aoi.onii.player.PlayerData;

import java.sql.*;
import java.util.UUID;

public class SQLitePlayerRepository {

    private final DatabaseManager db;
    private final Gson gson = new Gson();

    public SQLitePlayerRepository(DatabaseManager db) {
        this.db = db;
    }

    public PlayerData load(UUID uuid) {
        try (PreparedStatement ps = db.get().prepareStatement("SELECT json FROM players WHERE uuid=?")) {
            ps.setString(1, uuid.toString());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return gson.fromJson(rs.getString("json"), PlayerData.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new PlayerData(uuid);
    }

    public void save(PlayerData data) {
        try (PreparedStatement ps = db.get().prepareStatement("REPLACE INTO players VALUES(?,?)")) {
            ps.setString(1, data.getUuid().toString());
            ps.setString(2, gson.toJson(data));
            ps.executeUpdate();
            data.saved();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
