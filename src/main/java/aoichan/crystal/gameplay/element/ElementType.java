package aoichan.crystal.gameplay.element;

// [!] Code: Element enum
public enum ElementType {

    FIRE,
    FROST,
    THUNDER,
    HOLY,
    VOID,
    NONE;

    public static ElementType fromString(String s) {

        if (s == null) return NONE;

        try {

            return ElementType.valueOf(s.toUpperCase());

        } catch (Exception e) {

            return NONE;
        }
    }
}
