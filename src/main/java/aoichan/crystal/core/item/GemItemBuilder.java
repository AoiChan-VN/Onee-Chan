package aoichan.crystal.core.item;

import aoichan.crystal.core.gem.Gem;
import aoichan.crystal.CrystalPlugin;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.NamespacedKey;

import java.util.ArrayList;
import java.util.List;

// [!] Code: Build gem item
public class GemItemBuilder {

    public static ItemStack buildGem(
            Gem gem,
            int level
    ) {

        ItemStack item =
                new ItemStack(Material.AMETHYST_SHARD);

        ItemMeta meta =
                item.getItemMeta();

        if (meta == null)
            return item;

        meta.setDisplayName(
                "§d" + gem.getName() +
                " §7[Lv." + level + "]"
        );

        List<String> lore =
                new ArrayList<>();

        lore.add("§8▬▬▬▬▬▬▬▬▬▬▬▬");
        lore.add("§7Element: §e" +
                gem.getElement());
        lore.add("§7Level: §a" + level);
        lore.add("§8▬▬▬▬▬▬▬▬▬▬▬▬");

        meta.setLore(lore);

        PersistentDataContainer pdc =
                meta.getPersistentDataContainer();

        NamespacedKey gemKey =
                new NamespacedKey(
                        CrystalPlugin.getInstance(),
                        "gem_id"
                );

        NamespacedKey levelKey =
                new NamespacedKey(
                        CrystalPlugin.getInstance(),
                        "gem_level"
                );

        pdc.set(
                gemKey,
                PersistentDataType.STRING,
                gem.getId()
        );

        pdc.set(
                levelKey,
                PersistentDataType.INTEGER,
                level
        );

        item.setItemMeta(meta);

        return item;
    }

} 
