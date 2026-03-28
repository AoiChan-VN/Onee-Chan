package vn.aoi.onii.shop;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.*;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;
import vn.aoi.onii.Main;
import vn.aoi.onii.enums.WeaponTier;
import vn.aoi.onii.nbt.NBTManager;
import vn.aoi.onii.player.*;

import java.util.*;

public class ShopListener implements Listener {

    private final PlayerManager manager;
    private final Economy econ;

    public ShopListener(PlayerManager manager, Economy econ) {
        this.manager = manager;
        this.econ = econ;
    }

    @EventHandler
    public void click(InventoryClickEvent e) {

        if (!(e.getWhoClicked() instanceof Player player)) return;
        if (!e.getView().getTitle().contains("Cửa Hàng")) return;

        e.setCancelled(true);

        ItemStack clicked = e.getCurrentItem();
        if (clicked == null || !clicked.hasItemMeta()) return;

        YamlConfiguration config = YamlConfiguration.loadConfiguration(
                Main.getInstance().getShopFile()
        );

        for (String key : config.getConfigurationSection("shop.items").getKeys(false)) {

            String path = "shop.items." + key;

            String name = config.getString(path + ".name");

            if (!clicked.getItemMeta().getDisplayName().equals(name)) continue;

            double price = config.getDouble(path + ".price");

            if (!econ.has(player, price)) {
                player.sendMessage("§cKhông đủ linh thạch!");
                return;
            }

            econ.withdrawPlayer(player, price);

            String type = config.getString(path + ".type");

            // ===== WEAPON =====
            if (type.equalsIgnoreCase("WEAPON")) {

                Material mat = Material.valueOf(config.getString(path + ".material"));
                String tierStr = config.getString(path + ".tier");
                String effects = config.getString(path + ".effects");

                WeaponTier tier = WeaponTier.fromString(tierStr);

                ItemStack item = new ItemStack(mat);
                ItemMeta meta = item.getItemMeta();

                meta.setDisplayName(name);

                meta.setLore(Arrays.asList(
                        "§7Phẩm chất: §e" + tier.getDisplay(),
                        "§7Hiệu ứng: §a" + effects
                ));

                item.setItemMeta(meta);

                NBTManager.set(item, "tier", tier.name());
                NBTManager.set(item, "effects", effects);

                player.getInventory().addItem(item);
                player.sendMessage("§aĐã mua " + tier.getDisplay());
                return;
            }

            // ===== TECH =====
            if (type.equalsIgnoreCase("TECHNIQUE")) {

                String tech = config.getString(path + ".tech");

                PlayerData data = manager.get(player.getUniqueId(), player.getName());

                data.setTechnique(tech);
                manager.save(data);

                player.sendMessage("§aĐã học công pháp!");
                return;
            }
        }
    }
} 
