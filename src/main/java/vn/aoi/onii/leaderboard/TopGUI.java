package vn.aoi.onii.leaderboard;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;
import vn.aoi.onii.database.Database;

import java.sql.*;
import java.util.*;

public class TopGUI {

    private final Database db;

    public TopGUI(Database db) {
        this.db = db;
    }

    public Inventory create() {

        Inventory inv = Bukkit.createInventory(null, 27, "Bảng Xếp Hạng");

        try {
            PreparedStatement ps = db.getConnection().prepareStatement(
                    "SELECT name, realm, exp FROM players ORDER BY exp DESC LIMIT 10");
            ResultSet rs = ps.executeQuery();

            int slot = 0;

            while (rs.next()) {
                ItemStack item = new ItemStack(Material.PLAYER_HEAD);
                ItemMeta meta = item.getItemMeta();

                meta.setDisplayName("§e" + rs.getString("name"));
                meta.setLore(Arrays.asList(
                        "§7Cảnh giới: " + rs.getString("realm"),
                        "§7EXP: " + rs.getInt("exp")
                ));

                item.setItemMeta(meta);
                inv.setItem(slot++, item);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return inv;
    }
} 
