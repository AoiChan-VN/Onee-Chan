package aoichan.crystal.core.registry;

import aoichan.crystal.api.gem.GemDefinition;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class GemRegistry {

    // [!] Code: Registered Gems
    private final Map<String, GemDefinition> gems = new HashMap<>();

    // [!] Code: Register gem
    public void register(GemDefinition gem) {

        gems.put(gem.getId(), gem);
    }

    // [!] Code: Get gem by id
    public GemDefinition get(String id) {

        return gems.get(id);
    }

    // [!] Code: Get all gems
    public Collection<GemDefinition> getAll() {

        return gems.values();
    }

    // [!] Code: Clear registry (reload)
    public void clear() {

        gems.clear();
    }
}
