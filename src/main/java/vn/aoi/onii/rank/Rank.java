package vn.aoi.onii.rank;

import java.util.HashMap;
import java.util.Map;

public class Rank {

    private final String name;
    private final int maxLevel;
    private final String nextRank;
    private final boolean doKiep;

    private final Map<Integer, Integer> expMap = new HashMap<>();
    private int flatExp;

    public Rank(String name, int maxLevel, String nextRank, boolean doKiep) {
        this.name = name;
        this.maxLevel = maxLevel;
        this.nextRank = nextRank;
        this.doKiep = doKiep;
    }

    public void addLevel(int level, int exp, String phase) {
        expMap.put(level, exp);
    }

    public int getExp(int level) {
        return expMap.isEmpty() ? flatExp : expMap.getOrDefault(level, flatExp);
    }

    public String getNextRank() { return nextRank; }
    public int getMaxLevel() { return maxLevel; }
    public boolean isDoKiep() { return doKiep; } 
}
