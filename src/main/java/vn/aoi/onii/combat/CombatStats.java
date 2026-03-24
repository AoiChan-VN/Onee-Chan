package vn.aoi.onii.combat;

public class CombatStats {

    public static double applyDefense(double damage, double defense) {
        return damage * (100.0 / (100.0 + defense));
    }

    public static double applyPenetration(double defense, double pen) {
        return defense * (1.0 - pen);
    }
}
