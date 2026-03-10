package aoichan.crystal.gameplay.element;

// [!] Code: Element damage calculator
public class ElementDamageCalculator {

    public static double applyElementBonus(
            double baseDamage,
            ElementType element
    ) {

        switch (element) {

            case FIRE:

                return baseDamage * 1.15;

            case FROST:

                return baseDamage * 1.10;

            case THUNDER:

                return baseDamage * 1.20;

            case HOLY:

                return baseDamage * 1.12;

            case VOID:

                return baseDamage * 1.25;

            default:

                return baseDamage;
        }
    }

}
