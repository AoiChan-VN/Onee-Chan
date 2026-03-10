package aoichan.crystal.gameplay.element;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

// [!] Code: Element engine
public class ElementEngine {

    public static double applyElement(
            Player player,
            LivingEntity target,
            double damage,
            ElementType element
    ) {

        return ElementDamageCalculator
                .applyElementBonus(
                        damage,
                        element
                );
    }
} 
