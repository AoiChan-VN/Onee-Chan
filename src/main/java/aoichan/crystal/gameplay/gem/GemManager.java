package aoichan.crystal.gameplay.gem;

import aoichan.crystal.bootstrap.AoiMain;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class GemManager {

    private static final Map<String, Gem> gems = new HashMap<>();

    private static File file;
    private static YamlConfiguration config;

    public static void load() {

        file = new File(AoiMain.get().getDataFolder(), "gems.yml");

        if (!file.exists()) {
            AoiMain.get().saveResource("gems.yml", false);
        }

        config = YamlConfiguration.loadConfiguration(file);

        gems.clear();

        for (String id : config.getKeys(false)) {

            String name = config.getString(id + ".name");

            GemRarity rarity = GemRarity.valueOf(
                    config.getString(id + ".rarity").toUpperCase()
            );

            Map<GemStat, Double> stats = new HashMap<>();

            for (String stat : config.getConfigurationSection(id + ".stats").getKeys(false)) {

                GemStat gemStat = GemStat.valueOf(stat.toUpperCase());

                double value = config.getDouble(id + ".stats." + stat);

                stats.put(gemStat, value);

            }

            Gem gem = new Gem(id, name, rarity, stats);

            gems.put(id, gem);

        }

    }

    public static Gem getGem(String id) {

        return gems.get(id);

    }

}
