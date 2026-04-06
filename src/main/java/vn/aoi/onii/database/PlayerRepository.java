package vn.aoi.onii.database;

import vn.aoi.onii.model.Cultivator;

import java.sql.*;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class PlayerRepository {

    private final DatabaseManager database;

    public PlayerRepository(DatabaseManager database) {
        this.database = database;
        init();
    }

    private void init() {
        try (Connection conn = database.getConnection();
             PreparedStatement ps = conn.preparedStatement(sql)) {

            ps.execute("""
                CREATE TABLE IF NOT EXISTS players (
                    uuid TEXT PRIMARY KEY,
                    realm TEXT,
                    level INTEGER,
                    exp DOUBLE
                )
            """);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 🔍 LOAD
    public CompletableFuture<Cultivator> load(UUID uuid) {
        return CompletableFuture.supplyAsync(() -> {
            try (Connection conn = database.getConnection();
                 PreparedStatement ps = conn.prepareStatement(
                         "SELECT * FROM players WHERE uuid = ?")) {

                ps.setString(1, uuid.toString());
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    return Cultivator.builder()
                            .uuid(uuid)
                            .realm(rs.getString("realm"))
                            .level(rs.getInt("level"))
                            .exp(rs.getDouble("exp"))
                            .build();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

            return null;
        });
    }

    // 💾 SAVE / UPSERT
    public CompletableFuture<Void> save(Cultivator cultivator) {
        return CompletableFuture.runAsync(() -> {
            try (Connection conn = database.getConnection();
                 PreparedStatement ps = conn.prepareStatement("""
                        INSERT INTO players(uuid, realm, level, exp)
                        VALUES (?, ?, ?, ?)
                        ON CONFLICT(uuid) DO UPDATE SET
                        realm = excluded.realm,
                        level = excluded.level,
                        exp = excluded.exp
                 """)) {

                ps.setString(1, cultivator.getUuid().toString());
                ps.setString(2, cultivator.getRealm());
                ps.setInt(3, cultivator.getLevel());
                ps.setDouble(4, cultivator.getExp());

                ps.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    // ❌ DELETE
    public CompletableFuture<Void> delete(UUID uuid) {
        return CompletableFuture.runAsync(() -> {
            try (Connection conn = database.getConnection();
                 PreparedStatement ps = conn.prepareStatement(
                         "DELETE FROM players WHERE uuid = ?")) {

                ps.setString(1, uuid.toString());
                ps.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }
} 
