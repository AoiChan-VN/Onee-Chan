package aoichan.crystal.gameplay.gemlevel;

// [!] Code: Gem level data object
public class GemLevelData {

    private final int level;
    private final double statMultiplier;
    private final double effectMultiplier;

    public GemLevelData(
            int level,
            double statMultiplier,
            double effectMultiplier
    ) {

        this.level = level;
        this.statMultiplier = statMultiplier;
        this.effectMultiplier = effectMultiplier;
    }

    public int getLevel() {
        return level;
    }

    public double getStatMultiplier() {
        return statMultiplier;
    }

    public double getEffectMultiplier() {
        return effectMultiplier;
    }
} 
