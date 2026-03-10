package aoichan.crystal.gameplay.gemlevel;

import java.util.Map;
import java.util.HashMap;

// [!] Code: Gem level stat scaling engine
public class GemLevelEngine {

    public static Map<String, Double> scaleStats(

            Map<String, Double> baseStats,
            int level

    ) {

        GemLevelData data =
                GemLevelCalculator.calculate(level);

        Map<String, Double> scaled =
                new HashMap<>();

        for (Map.Entry<String, Double> entry : baseStats.entrySet()) {

            scaled.put(

                    entry.getKey(),

                    entry.getValue() *
                            data.getStatMultiplier()

            );
        }

        return scaled;
    }

} 
