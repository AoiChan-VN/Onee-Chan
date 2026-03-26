package vn.aoi.onii.player;

import vn.aoi.onii.database.Database;
import vn.aoi.onii.enums.Realm;
import vn.aoi.onii.enums.Stage;

import java.sql.*;
import java.util.*;

public class PlayerManager {

    private final Database db;
    private final Map<UUID, PlayerData> cache = new HashMap<>();

    public PlayerManager(Database db) {
        this.db = db;
    }

    public PlayerData get(UUID uuid, String name) {

        if (cache.containsKey(uuid)) return cache.get(uuid);

        try {
            PreparedStatement ps = db.get().prepareStatement("SELECT * FROM players WHERE uuid=?");
            ps.setString(1, uuid.toString());

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                PlayerData data = new PlayerData(uuid, name);
                data.setRealm(Realm.valueOf(rs.getString("realm")));
                data.setStage(Stage.valueOf(rs.getString("stage")));
                data.setSect(rs.getString("sect"));
                data.setTechnique(rs.getString("technique"));

                cache.put(uuid, data);
                return data;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        PlayerData data = new PlayerData(uuid, name);
        save(data);
        cache.put(uuid, data);
        return data;
    }

    public void save(PlayerData d) {
        try {
            PreparedStatement ps = db.get().prepareStatement("""
                INSERT OR REPLACE INTO players(uuid,name,realm,stage,sect,technique)
                VALUES(?,?,?,?,?,?)
            """);

            ps.setString(1, d.getUuid().toString());
            ps.setString(2, d.getName());
            ps.setString(3, d.getRealm().name());
            ps.setString(4, d.getStage().name());
            ps.setString(5, d.getSect());
            ps.setString(6, d.getTechnique());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
