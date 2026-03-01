package aoichan.crystal.core;

import aoichan.crystal.AoiMain;
import aoichan.crystal.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.*;

public class GemsManager {

    private final Map<String, Map<String, Object>> gems = new HashMap<>();
    private final AoiMain plugin;

    public GemsManager(AoiMain plugin) {
        this.plugin = plugin;
        reload();
    }

    public void reload() {
        gems.clear();
        File file = new File(plugin.getDataFolder(), "gems.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

        for (String key : config.getConfigurationSection("gems").getKeys(false)) {
            gems.put(key,
                    config.getConfigurationSection("gems." + key)
                            .getValues(false));
        }
    }

    public Set<String> getAllGems() {
        return gems.keySet();
    }

    public ItemStack buildGem(String id) {
        Map<String, Object> data = gems.get(id);
        if (data == null) return new ItemStack(Material.BARRIER);

        return new ItemBuilder(Material.DIAMOND)
                .setName((String) data.get("display-name"))
                .build();
    }
}
