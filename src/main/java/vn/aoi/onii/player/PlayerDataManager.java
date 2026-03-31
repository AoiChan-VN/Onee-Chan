package vn.aoi.onii.player;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.*;
import vn.aoi.onii.Main;

import java.sql.*;
import java.util.*;

public class PlayerDataManager implements Listener {

    private final Main plugin;
    private final Map<UUID, PlayerData> cache = new HashMap<>();

    public PlayerDataManager(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> load(p));
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> save(p));
    }

    @EventHandler
    public void onKill(EntityDeathEvent e) {
        if (e.getEntity().getKiller() == null) return;
        Player p = e.getEntity().getKiller();
        PlayerData d = get(p);
        if (d == null) return;

        d.addExp(20);
    }

    private void load(Player p) {
        try {
            PreparedStatement ps = plugin.getDatabaseManager().getConnection()
                    .prepareStatement("SELECT * FROM players WHERE uuid=?");
            ps.setString(1, p.getUniqueId().toString());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                cache.put(p.getUniqueId(), new PlayerData(
                        rs.getString("rank"),
                        rs.getInt("level"),
                        rs.getInt("exp")
                ));
            } else {
                PlayerData d = new PlayerData("Phàm nhân", 1, 0);
                cache.put(p.getUniqueId(), d);
                save(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void save(Player p) {
        try {
            PlayerData d = cache.get(p.getUniqueId());
            if (d == null) return;

            PreparedStatement ps = plugin.getDatabaseManager().getConnection()
                    .prepareStatement("REPLACE INTO players VALUES(?,?,?,?)");

            ps.setString(1, p.getUniqueId().toString());
            ps.setString(2, d.getRank());
            ps.setInt(3, d.getLevel());
            ps.setInt(4, d.getExp());
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public PlayerData get(Player p) {
        return cache.get(p.getUniqueId());
    }
}
