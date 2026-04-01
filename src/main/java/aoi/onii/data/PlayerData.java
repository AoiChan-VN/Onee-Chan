package vn.aoi.onii.data;

import java.util.UUID;

public class PlayerData {

    private final UUID uuid;
    private String realm;
    private int level;
    private int exp;

    public PlayerData(UUID uuid, String realm, int level, int exp) {
        this.uuid = uuid;
        this.realm = realm;
        this.level = level;
        this.exp = exp;
    }

    public UUID getUuid() { return uuid; }
    public String getRealm() { return realm; }
    public void setRealm(String realm) { this.realm = realm; }

    public int getLevel() { return level; }
    public void setLevel(int level) { this.level = level; }

    public int getExp() { return exp; }
    public void addExp(int amount) { this.exp += amount; }
} 
