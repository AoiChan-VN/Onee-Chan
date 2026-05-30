package vn.aoi.cultivation.gui.menu;

import vn.aoi.cultivation.core.security.AntiDupeManager;
import vn.aoi.cultivation.gui.holder.CustomInventoryHolder;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class MainMenu {

    private static final String GUI_ID = "main_menu";
    private static final int SIZE = 27;

    private final AntiDupeManager antiDupeManager;

    public MainMenu(AntiDupeManager antiDupeManager) {
        this.antiDupeManager = antiDupeManager;
    }

    public void open(Player player) {

        CustomInventoryHolder holder =
                new CustomInventoryHolder(GUI_ID);

        Inventory inventory =
                Bukkit.createInventory(
                        holder,
                        SIZE,
                        "Cultivation"
                );

        holder.setInventory(inventory);

        buildMenu(inventory);

        /*
         * Snapshot phải được tạo trước khi mở GUI
         * để InventoryCloseListener có dữ liệu rollback
         */
        antiDupeManager.startSession(
                player,
                inventory
        );

        player.openInventory(inventory);
    }

    private void buildMenu(Inventory inventory) {

        inventory.setItem(
                11,
                createItem(
                        Material.NETHER_STAR,
                        "§bCảnh Giới",
                        List.of(
                                "§7Xem tiến trình tu luyện",
                                "§7và cảnh giới hiện tại"
                        )
                )
        );

        inventory.setItem(
                13,
                createItem(
                        Material.EMERALD,
                        "§aCửa Hàng",
                        List.of(
                                "§7Mở giao diện trao đổi",
                                "§7linh thạch và vật phẩm"
                        )
                )
        );

        inventory.setItem(
                15,
                createItem(
                        Material.BOOK,
                        "§eThông Tin",
                        List.of(
                                "§7Xem thống kê",
                                "§7tu vi và công đức"
                        )
                )
        );
    }

    private ItemStack createItem(Material material,
                                 String displayName,
                                 List<String> loreLines) {

        ItemStack item = new ItemStack(material);

        ItemMeta meta = item.getItemMeta();

        if (meta == null) {
            return item;
        }

        meta.setDisplayName(displayName);

        List<String> lore = new ArrayList<>(loreLines);

        meta.setLore(lore);

        item.setItemMeta(meta);

        return item;
    }
} 
