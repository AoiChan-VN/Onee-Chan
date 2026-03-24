package vn.aoi.onii.combat;

import java.util.EnumMap;

public class ElementProfile {

    private final EnumMap<ElementType, Double> attack = new EnumMap<>(ElementType.class);
    private final EnumMap<ElementType, Double> resist = new EnumMap<>(ElementType.class);

    public ElementProfile() {
        for (ElementType t : ElementType.values()) {
            attack.put(t, 0.0);
            resist.put(t, 0.0);
        }
    }

    public double getAttack(ElementType type) { return attack.get(type); }
    public double getResist(ElementType type) { return resist.get(type); }

    public void addAttack(ElementType type, double value) { attack.put(type, attack.get(type) + value); }
    public void addResist(ElementType type, double value) { resist.put(type, resist.get(type) + value); }
}
