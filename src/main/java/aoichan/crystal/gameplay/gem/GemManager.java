package aoichan.crystal.gameplay.gem;

import aoichan.crystal.api.gem.GemDefinition;
import aoichan.crystal.core.registry.GemRegistry;
import org.bukkit.inventory.ItemStack;

public class GemManager {

    // [!] Code: Gem registry
    private final GemRegistry registry;

    public GemManager(GemRegistry registry) {
        this.registry = registry;
    }

    // [!] Code: Create gem item
    public ItemStack createItem(String id) {

        GemDefinition gem = registry.get(id);

        if (gem == null) return null;

        return GemItemFactory.create(gem);
    }

    // [!] Code: Get gem definition
    public GemDefinition get(String id) {

        return registry.get(id);
    }
} 
