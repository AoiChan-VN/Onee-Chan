package aoichan.crystal.engine.stats;

import aoichan.crystal.api.gem.GemDefinition;
import aoichan.crystal.api.stat.PlayerStats;
import aoichan.crystal.api.stat.StatType;

import java.util.Map;

public class StatCalculator {

    // [!] Code: Apply gem stats
    public static void applyGem(PlayerStats stats, GemDefinition gem) {

        Map<String, Double> gemStats = gem.getStats();

        for (String key : gemStats.keySet()) {

            try {

                StatType type = StatType.valueOf(key);

                stats.add(type, gemStats.get(key));

            } catch (Exception ignored) {
            }
        }
    }
}
