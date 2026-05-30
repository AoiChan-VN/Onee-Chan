package vn.aoi.cultivation.model.cultivator;

public enum SpiritRoot {

    THIEN_LINH(3.0, 0.005),
    BIEN_LINH(2.5, 0.02),
    NHI_LINH(2.0, 0.08),
    TAM_LINH(1.5, 0.20),
    TU_LINH(1.2, 0.30),
    NGU_LINH(1.0, 0.395);

    private final double cultivationMultiplier;
    private final double rarity;

    SpiritRoot(double cultivationMultiplier, double rarity) {
        this.cultivationMultiplier = cultivationMultiplier;
        this.rarity = rarity;
    }

    public double multiplier() {
        return cultivationMultiplier;
    }

    public double rarity() {
        return rarity;
    }
} 
