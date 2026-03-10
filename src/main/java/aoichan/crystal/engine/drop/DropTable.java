package aoichan.crystal.gameplay.drop;

// [!] Code: Gem drop entry
public class DropTable {

    private final String gemId;
    private final int level;
    private final double chance;

    public DropTable(String gemId, int level, double chance) {
        this.gemId = gemId;
        this.level = level;
        this.chance = chance;
    }

    public String getGemId() {
        return gemId;
    }

    public int getLevel() {
        return level;
    }

    public double getChance() {
        return chance;
    }
} 
