package vn.aoi.onii.player;

import org.bukkit.entity.Player;
import vn.aoi.onii.Main;
import vn.aoi.onii.utils.AsyncUtil;

import java.sql.*;
import java.util.*;

public class PlayerManager {

    private final Main plugin;
    private final Map<UUID, PlayerData> cache = new HashMap<>();

    public PlayerManager(Main plugin) {
        this.plugin = plugin;
    }

    public void load(Player player) {
        AsyncUtil.runAsync(() -> {
            try {
                PreparedStatement ps = plugin.getDatabaseManager().getConnection()
                        .prepareStatement("SELECT * FROM players WHERE uuid=?");
                ps.setString(1, player.getUniqueId().toString());

                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    cache.put(player.getUniqueId(), new PlayerData(
                            rs.getString("realm"),
                            rs.getInt("level"),
                            rs.getInt("exp")
                    ));
                } else {
                    cache.put(player.getUniqueId(), new PlayerData("Phàm nhân", 1, 0));
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public void save(Player player) {
        PlayerData data = cache.get(player.getUniqueId());
        if (data == null) return;

        AsyncUtil.runAsync(() -> {
            try {
                PreparedStatement ps = plugin.getDatabaseManager().getConnection()
                        .prepareStatement("REPLACE INTO players VALUES (?,?,?,?)");

                ps.setString(1, player.getUniqueId().toString());
                ps.setString(2, data.getRealm());
                ps.setInt(3, data.getLevel());
                ps.setInt(4, data.getExp());

                ps.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public void saveAll() {
        cache.keySet().forEach(uuid -> {
            Player player = plugin.getServer().getPlayer(uuid);
            if (player != null) save(player);
        });
    }

    public PlayerData get(Player player) {
        return cache.get(player.getUniqueId());
    }
  } 
