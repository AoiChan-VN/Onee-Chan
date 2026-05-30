package vn.aoi.cultivation.service;

import vn.aoi.cultivation.CultivationPlugin;
import vn.aoi.cultivation.config.ShopConfig.ShopItem;

import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public final class ShopItemFactory {

    public static final String SHOP_ITEM_KEY = "shop_item_id";

    private final NamespacedKey shopItemKey;

    public ShopItemFactory(CultivationPlugin plugin) {

        this.shopItemKey =
                new NamespacedKey(
                        plugin,
                        SHOP_ITEM_KEY
                );
    }

    public ItemStack createDisplayItem(ShopItem shopItem) {

        ItemStack item =
                new ItemStack(
                        shopItem.getMaterial(),
                        Math.max(1, shopItem.getAmount())
                );

        ItemMeta meta =
                item.getItemMeta();

        if (meta == null) {
            return item;
        }

        meta.setDisplayName(
                ChatColor.translateAlternateColorCodes(
                        '&',
                        shopItem.getDisplayName()
                )
        );

        List<String> lore =
                new ArrayList<>();

        lore.add("§7Giá mua: §a" + shopItem.getBuyPrice());
        lore.add("§7Giá bán: §c" + shopItem.getSellPrice());

        PersistentDataContainer pdc =
                meta.getPersistentDataContainer();

        pdc.set(
                shopItemKey,
                PersistentDataType.STRING,
                shopItem.getId()
        );

        meta.setLore(lore);

        meta.addItemFlags(
                ItemFlag.HIDE_ATTRIBUTES
        );

        item.setItemMeta(meta);

        return item;
    }

    public String getShopId(ItemStack item) {

        if (item == null) {
            return null;
        }

        if (!item.hasItemMeta()) {
            return null;
        }

        ItemMeta meta =
                item.getItemMeta();

        if (meta == null) {
            return null;
        }

        PersistentDataContainer pdc =
                meta.getPersistentDataContainer();

        if (!pdc.has(
                shopItemKey,
                PersistentDataType.STRING
        )) {
            return null;
        }

        return pdc.get(
                shopItemKey,
                PersistentDataType.STRING
        );
    }

    public boolean isShopItem(ItemStack item) {

        return getShopId(item) != null;
    }
} 
