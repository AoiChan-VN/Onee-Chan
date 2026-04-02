package vn.aoi.onii.manager.model;

import java.util.Map;

public class Realm {

    private final String name;
    private final int maxLevel;
    private final Map<Integer, LevelInfo> levels;
    private final String next;
    private final boolean tribulation;

    public Realm(String name, int maxLevel, Map<Integer, LevelInfo> levels, String next, boolean tribulation) {
        this.name = name;
        this.maxLevel = maxLevel;
        this.levels = levels;
        this.next = next;
        this.tribulation = tribulation;
    }

    public String getName() { return name; }
    public int getMaxLevel() { return maxLevel; }
    public Map<Integer, LevelInfo> getLevels() { return levels; }
    public String getNext() { return next; }
    public boolean isTribulation() { return tribulation; }
}
