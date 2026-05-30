package vn.aoi.cultivation.model.cultivator;

public enum SpiritElement {

    KIM,
    MOC,
    THUY,
    HOA,
    THO;

    public boolean counters(SpiritElement other) {
        return switch (this) {
            case HOA -> other == KIM;
            case KIM -> other == MOC;
            case MOC -> other == THO;
            case THO -> other == THUY;
            case THUY -> other == HOA;
        };
    }
} 
