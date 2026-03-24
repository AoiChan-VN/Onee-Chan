package vn.aoi.onii.combat;

import java.util.EnumMap;

public class StatProfile {

    private final EnumMap<StatType, Double> stats = new EnumMap<>(StatType.class);

    public StatProfile() {
        for (StatType t : StatType.values()) {
            stats.put(t, 0.0);
        }
    }

    public double get(StatType type) {
        return stats.get(type);
    }

    public void set(StatType type, double value) {
        stats.put(type, value);
    }

    public void add(StatType type, double value) {
        stats.put(type, stats.get(type) + value);
    }
}
