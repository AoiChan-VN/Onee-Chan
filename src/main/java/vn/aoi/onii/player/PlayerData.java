package vn.aoi.onii.player;

import vn.aoi.onii.combat.*;
import java.util.UUID;

public class PlayerData {

    private UUID uuid;

    private int level = 1;
    private int exp = 0;

    private int mana = 100;
    private int maxMana = 100;

    private String playerClass;

    private StatProfile stats = new StatProfile();
    private ElementProfile elements = new ElementProfile();

    private transient boolean dirty;

    public PlayerData() {}

    public PlayerData(UUID uuid) { this.uuid = uuid; }

    public StatProfile getStats() {
        if (stats == null) stats = new StatProfile();
        return stats;
    }

    public ElementProfile getElements() {
        if (elements == null) elements = new ElementProfile();
        return elements;
    }

    public boolean hasMana(int amount) { return mana >= amount; }

    public void consumeMana(int amount) {
        mana -= amount;
        if (mana < 0) mana = 0;
        dirty = true;
    }

    public void regenMana(int amount) {
        mana += amount;
        if (mana > maxMana) mana = maxMana;
        dirty = true;
    }

    public String getPlayerClass() { return playerClass; }

    public void setPlayerClass(String playerClass) {
        this.playerClass = playerClass;
        this.dirty = true;
    }

    public boolean isDirty() { return dirty; }
    public void saved() { dirty = false; }

    public UUID getUuid() { return uuid; }

    public int getLevel() { return level; }

    public void addExp(int amount) {
        exp += amount;
        while (exp >= level * 100) {
            exp -= level * 100;
            level++;
        }
        dirty = true;
    }
}
