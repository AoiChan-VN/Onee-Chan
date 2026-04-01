package vn.aoi.onii.cultivation;

import java.util.Map;

public class Realm {

    private final String name;
    private final int maxLevel;
    private final Map<Integer, Integer> expMap;
    private final String nextRank;
    private final boolean thienKiep;

    public Realm(String name, int maxLevel, Map<Integer, Integer> expMap, String nextRank, boolean thienKiep) {
        this.name = name;
        this.maxLevel = maxLevel;
        this.expMap = expMap;
        this.nextRank = nextRank;
        this.thienKiep = thienKiep;
    }

    public int getRequiredExp(int level) {
        return expMap.getOrDefault(level, Integer.MAX_VALUE);
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public String getNextRank() {
        return nextRank;
    }

    public boolean isThienKiep() {
        return thienKiep;
    }

    public String getName() {
        return name;
    }
} 
