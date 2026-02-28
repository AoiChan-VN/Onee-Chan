package aoichan.crystal.storage;

import aoidev.crystal.gem.Gem;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.*;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class SQLiteStorage implements StorageAdapter {

    private final HikariDataSource ds;
    private final Executor executor;

    public SQLiteStorage(HikariDataSource ds, Executor executor) {
        this.ds = ds;
        this.executor = executor;
        createTable();
    }

    private void createTable() {
        try (Connection c = ds.getConnection();
             Statement s = c.createStatement()) {

            s.execute("""
                    CREATE TABLE IF NOT EXISTS gems (
                        id TEXT PRIMARY KEY,
                        type TEXT NOT NULL,
                        level INTEGER NOT NULL
                    );
                    """);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public CompletableFuture<Void> save(Gem gem) {
        return CompletableFuture.runAsync(() -> {
            try (Connection c = ds.getConnection();
                 PreparedStatement ps = c.prepareStatement(
                         "REPLACE INTO gems VALUES (?,?,?)")) {

                ps.setString(1, gem.getId().toString());
                ps.setString(2, gem.getType());
                ps.setInt(3, gem.getLevel());
                ps.executeUpdate();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }, executor);
    }

    @Override
    public CompletableFuture<Void> delete(UUID id) {
        return CompletableFuture.runAsync(() -> {
            try (Connection c = ds.getConnection();
                 PreparedStatement ps = c.prepareStatement(
                         "DELETE FROM gems WHERE id=?")) {

                ps.setString(1, id.toString());
                ps.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, executor);
    }

    @Override
    public CompletableFuture<List<Gem>> loadAll() {
        return CompletableFuture.supplyAsync(() -> {

            List<Gem> list = new ArrayList<>();

            try (Connection c = ds.getConnection();
                 Statement s = c.createStatement();
                 ResultSet rs = s.executeQuery("SELECT * FROM gems")) {

                while (rs.next()) {
                    list.add(new Gem(
                            UUID.fromString(rs.getString("id")),
                            rs.getString("type"),
                            rs.getInt("level")
                    ));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return list;

        }, executor);
    }

    @Override
    public void shutdown() {
        ds.close();
    }
}
