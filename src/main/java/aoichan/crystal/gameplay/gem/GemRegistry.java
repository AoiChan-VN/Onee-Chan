package aoichan.crystal.gameplay.gem;

import aoichan.crystal.bootstrap.CrystalPlugin;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class GemRegistry {

    private static final Map<String, ConfigurationSection> gems =
            new HashMap<>();

    // [!] Code: Load gems.yml
    public static void load() {

        gems.clear();

        File file = new File(
                CrystalPlugin.get().getDataFolder(),
                "gems.yml"
        );

        YamlConfiguration config =
                YamlConfiguration.loadConfiguration(file);

        ConfigurationSection section =
                config.getConfigurationSection("gems");

        if (section == null) return;

        for (String key : section.getKeys(false)) {

            gems.put(key, section.getConfigurationSection(key));
        }
    }

    // [!] Code: Get gem
    public static ConfigurationSection get(String id) {

        return gems.get(id);
    }
}
