package vn.aoi.onii.player;

import vn.aoi.onii.cultivation.CultivationStage;

public class PlayerData {

    private int cultivationPower;
    private CultivationStage stage;

    public PlayerData() {
        this.cultivationPower = 0;
        this.stage = CultivationStage.PHAM_NHAN;
    }

    public void addPower(int amount) {
        cultivationPower += amount;
    }

    public int getPower() { return cultivationPower; }

    public CultivationStage getStage() { return stage; }

    public void setStage(CultivationStage stage) {
        this.stage = stage;
    }
} 
