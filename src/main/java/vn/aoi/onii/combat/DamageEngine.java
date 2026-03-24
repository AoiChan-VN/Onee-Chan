package vn.aoi.onii.combat;

import java.util.concurrent.ThreadLocalRandom;

public class DamageEngine {

    public static DamageResult calculate(StatProfile attacker, StatProfile defender, double base) {

        DamageResult result = new DamageResult();

        double str = attacker.get(StatType.STR);
        double def = defender.get(StatType.DEF);
        double critChance = attacker.get(StatType.CRIT);

        double damage = base + (str * 0.5);

        damage *= (100.0 / (100.0 + def));

        boolean crit = ThreadLocalRandom.current().nextDouble() < critChance;
        if (crit) {
            damage *= 1.5;
        }

        result.damage = damage;
        result.crit = crit;

        return result;
    }
} 
