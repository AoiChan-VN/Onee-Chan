package vn.aoi.onii.player;

import java.util.UUID;

public class PlayerData {

    private UUID uuid;
    private int level = 1;
    private int exp = 0;

    private transient boolean dirty;
    
    private String playerClass;

    public String getPlayerClass() { return playerClass; }

    public void setPlayerClass(String playerClass) {
        this.playerClass = playerClass;
        this.dirty = true;
    }

    public PlayerData(UUID uuid) {
        this.uuid = uuid;
    }

    public void addExp(int amount) {
        exp += amount;
        while (exp >= level * 100) {
            exp -= level * 100;
            level++;
        }
        dirty = true;
    }

    public boolean isDirty() { return dirty; }
    public void saved() { dirty = false; }

    public UUID getUuid() { return uuid; }
    public int getLevel() { return level; }
}
