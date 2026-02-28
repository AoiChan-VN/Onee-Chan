package aoidev.crystal;

import java.util.Objects;
import java.util.UUID;

/**
 * Basic immutable gem model. Extend with attributes, rarity, stats, nbt, etc.
 */
public final class Gem {
    private final UUID id;
    private final String type;
    private final int level;

    public Gem(UUID id, String type, int level) {
        this.id = id;
        this.type = type;
        this.level = level;
    }

    public UUID getId() { return id; }
    public String getType() { return type; }
    public int getLevel() { return level; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Gem)) return false;
        Gem gem = (Gem) o;
        return id.equals(gem.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
