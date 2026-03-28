package vn.aoi.onii.shop;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import vn.aoi.onii.Main;

import java.util.*;

public class ShopManager {

    private final FileConfiguration config;
    private final Map<Integer, Inventory> cache = new HashMap<>();

    private static final int ITEMS_PER_PAGE = 28;

    public ShopManager() {
        this.config = Main.getInstance().getShopConfig();
    }

    public Inventory openShop(int page) {

        int maxPage = getMaxPage();
        if (maxPage == 0) {
            return createErrorInventory("Shop rỗng!");
        }

        if (page < 1) page = 1;
        if (page > maxPage) page = maxPage;

        // Cache hit ⚡
        if (cache.containsKey(page)) {
            return cache.get(page);
        }

        Inventory inv = buildPage(page);
        cache.put(page, inv);
        return inv;
    }

    private Inventory buildPage(int page) {

        ConfigurationSection section = config.getConfigurationSection("shop.items");

        if (section == null) {
            return createErrorInventory("shop.items thiếu!");
        }

        List<String> keys = new ArrayList<>(section.getKeys(false));

        String title = config.getString("shop.title", "Shop") + " §7[" + page + "]";
        Inventory inv = Bukkit.createInventory(null, 54, title);

        fillGlass(inv);

        int start = (page - 1) * ITEMS_PER_PAGE;
        int end = Math.min(start + ITEMS_PER_PAGE, keys.size());

        int slot = 10;

        for (int i = start; i < end; i++) {

            String path = "shop.items." + keys.get(i);

            Material mat = Material.matchMaterial(config.getString(path + ".material", ""));
            if (mat == null) continue;

            ItemStack item = new ItemStack(mat);
            ItemMeta meta = item.getItemMeta();
            if (meta == null) continue;

            meta.setDisplayName(config.getString(path + ".name", "Item"));

            List<String> lore = new ArrayList<>();
            lore.add("§7Cấp: §e" + config.getString(path + ".tier", "1"));
            lore.add("§7Giá: §a" + config.getDouble(path + ".price", 0));

            meta.setLore(lore);
            item.setItemMeta(meta);

            inv.setItem(slot, item);

            slot++;
            if (slot % 9 == 8) slot += 2;
        }

        // Navigation
        inv.setItem(45, createNav("§e⬅ Trang trước", "§7Quay về trang trước"));
        inv.setItem(53, createNav("§eTrang sau ➡", "§7Sang trang tiếp"));

        return inv;
    }

    private void fillGlass(Inventory inv) {
        ItemStack glass = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta meta = glass.getItemMeta();

        if (meta != null) {
            meta.setDisplayName(" ");
            glass.setItemMeta(meta);
        }

        for (int i = 0; i < inv.getSize(); i++) {
            if (i < 10 || i > 43 || i % 9 == 0 || i % 9 == 8) {
                inv.setItem(i, glass);
            }
        }
    }

    private ItemStack createNav(String name, String loreText) {
        ItemStack item = new ItemStack(Material.ARROW);
        ItemMeta meta = item.getItemMeta();

        if (meta != null) {
            meta.setDisplayName(name);
            meta.setLore(Collections.singletonList(loreText));
            item.setItemMeta(meta);
        }

        return item;
    }

    private int getMaxPage() {
        ConfigurationSection section = config.getConfigurationSection("shop.items");
        if (section == null) return 0;

        int size = section.getKeys(false).size();
        return (int) Math.ceil((double) size / ITEMS_PER_PAGE);
    }

    private Inventory createErrorInventory(String msg) {
        Inventory inv = Bukkit.createInventory(null, 27, "Shop Error");
        ItemStack item = new ItemStack(Material.BARRIER);
        ItemMeta meta = item.getItemMeta();

        if (meta != null) {
            meta.setDisplayName("§c" + msg);
            item.setItemMeta(meta);
        }

        inv.setItem(13, item);
        return inv;
    }

    public void clearCache() {
        cache.clear();
    }
}
