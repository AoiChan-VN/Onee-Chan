package vn.aoi.onii.database;

import vn.aoi.onii.model.Cultivator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class PlayerRepository {

    private final DatabaseManager db;
    private final DatabaseExecutor executor;

    public PlayerRepository(DatabaseManager db, DatabaseExecutor executor) {
        this.db = db;
        this.executor = executor;
    }

    // ================= LOAD =================

    public CompletableFuture<Cultivator> load(UUID uuid) {
        return executor.supply(() -> {

            try (Connection conn = db.getConnection();
                 PreparedStatement ps = conn.prepareStatement(
                         "SELECT * FROM cultivators WHERE uuid=?")) {

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

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        });
    }

    // ================= SAVE =================

    public void save(Cultivator c) {

        executor.execute(() -> {

            try (Connection conn = db.getConnection();
                 PreparedStatement ps = conn.prepareStatement(
                         "REPLACE INTO cultivators (uuid, realm, level, exp) VALUES (?, ?, ?, ?)")) {

                ps.setString(1, c.getUuid().toString());
                ps.setString(2, c.getRealm());
                ps.setInt(3, c.getLevel());
                ps.setDouble(4, c.getExp());

                ps.executeUpdate();

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
