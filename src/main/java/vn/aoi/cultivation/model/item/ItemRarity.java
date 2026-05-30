package vn.aoi.cultivation.model.item;

public enum ItemRarity {

    THUONG(1),
    LINH(2),
    QUY(3),
    THIEN(4),
    THAN(5),
    TIEN(6);

    private final int power;

    ItemRarity(int power) {
        this.power = power;
    }

    public int power() {
        return power;
    }

    public boolean isAbove(ItemRarity other) {
        return this.power > other.power;
    }
} 
