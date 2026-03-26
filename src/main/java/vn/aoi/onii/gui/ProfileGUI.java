package vn.aoi.onii.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;
import vn.aoi.onii.player.PlayerData;

import java.util.Arrays;

public class ProfileGUI {

    public static Inventory create(PlayerData data) {

        Inventory inv = Bukkit.createInventory(null, 27, "Thông Tin Tu Tiên");

        ItemStack info = new ItemStack(Material.NETHER_STAR);
        ItemMeta meta = info.getItemMeta();

        meta.setDisplayName("§6Thông tin");

        meta.setLore(Arrays.asList(
                "§7Cảnh giới: §e" + data.getRealm().getDisplay(),
                "§7Tu vi: §e" + data.getStage().getDisplay(),
                "§7EXP: §e" + data.getExp(),
                "§7Công pháp: §e" + data.getTechnique(),
                "§7Tông môn: §e" + data.getSect()
        ));

        info.setItemMeta(meta);
        inv.setItem(13, info);

        return inv;
    }
}
