package vn.aoi.cultivation.model.skill;

public enum SkillTier {

    PHAM(1),
    LINH(2),
    DIA(3),
    THIEN(4),
    TIEN(5);

    private final int level;

    SkillTier(int level) {
        this.level = level;
    }

    public int level() {
        return level;
    }

    public boolean isHigherThan(SkillTier other) {
        return this.level > other.level;
    }
} 
