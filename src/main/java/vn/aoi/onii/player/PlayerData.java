package vn.aoi.onii.player;

public class PlayerData {

    private String rank;
    private int level;
    private int exp;

    public PlayerData(String rank, int level, int exp) {
        this.rank = rank;
        this.level = level;
        this.exp = exp;
    }

    public String getRank() { return rank; }
    public int getLevel() { return level; }
    public int getExp() { return exp; }

    public void addExp(int amount) {
        this.exp += amount;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }
} 
