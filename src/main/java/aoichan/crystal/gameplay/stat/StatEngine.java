package aoichan.crystal.gameplay.stat;

import aoichan.crystal.gameplay.gem.GemRegistry;
import aoichan.crystal.gameplay.socket.GemData;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatEngine {

    // [!] Code: Collect stats
    public static Map<String, Integer> collect(
            List<GemData> gems
    ) {

        Map<String, Integer> stats = new HashMap<>();

        for (GemData gem : gems) {

            ConfigurationSection config =
                    GemRegistry.get(gem.getId());

            if (config == null) continue;

            ConfigurationSection level =
                    config.getConfigurationSection(
                            "levels." + gem.getLevel()
                    );

            if (level == null) continue;

            for (String stat : level.getKeys(false)) {

                int value = level.getInt(stat);

                stats.put(
                        stat,
                        stats.getOrDefault(stat, 0) + value
                );
            }
        }

        return stats;
    }
} 
