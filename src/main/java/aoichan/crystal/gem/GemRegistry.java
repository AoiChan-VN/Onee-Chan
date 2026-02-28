package aoidev.crystal.gem;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Allows infinite gem types.
 */
public class GemRegistry {

    private final Set<String> registeredTypes = ConcurrentHashMap.newKeySet();

    public void register(String type) {
        registeredTypes.add(type.toLowerCase());
    }

    public boolean exists(String type) {
        return registeredTypes.contains(type.toLowerCase());
    }

    public Set<String> getAllTypes() {
        return registeredTypes;
    }
}
