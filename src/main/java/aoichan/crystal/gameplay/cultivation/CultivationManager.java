package aoichan.crystal.gameplay.cultivation;

import aoichan.crystal.bootstrap.AoiMain;
import aoichan.crystal.gameplay.stat.StatType;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class CultivationManager {

    private static final Map<String, CultivationRealm> realms = new HashMap<>();

    public static void load() {

        File file = new File(
                AoiMain.get().getDataFolder(),
                "realms.yml"
        );

        if (!file.exists()) {
            AoiMain.get().saveResource("realms.yml", false);
        }

        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

        realms.clear();

        for (String id : config.getKeys(false)) {

            String name = config.getString(id + ".name");

            Map<StatType, Double> stats = new HashMap<>();

            for (String stat : config.getConfigurationSection(id + ".stats").getKeys(false)) {

                StatType type = StatType.valueOf(stat.toUpperCase());

                double value = config.getDouble(id + ".stats." + stat);

                stats.put(type, value);

            }

            CultivationRealm realm = new CultivationRealm(
                    id,
                    name,
                    stats
            );

            realms.put(id, realm);

        }

    }

    public static CultivationRealm getRealm(String id) {

        return realms.get(id);

    }

} 
