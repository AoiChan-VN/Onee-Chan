package vn.aoi.cultivation.service;

import vn.aoi.cultivation.config.ShopConfig;
import vn.aoi.cultivation.config.ShopConfig.ShopItem;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public final class ShopService {

    private final ShopConfig shopConfig;

    public ShopService(ShopConfig shopConfig) {
        this.shopConfig = shopConfig;
    }

    public Collection<ShopItem> getShopItems() {

        return Collections.unmodifiableCollection(
                shopConfig.getShopItems().values()
        );
    }

    public ShopItem getShopItem(String id) {

        if (id == null) {
            return null;
        }

        return shopConfig.getItem(id);
    }

    public boolean contains(String id) {

        if (id == null) {
            return false;
        }

        return shopConfig.contains(id);
    }

    public Map<String, ShopItem> getShopItemMap() {

        return Collections.unmodifiableMap(
                shopConfig.getShopItems()
        );
    }
}
