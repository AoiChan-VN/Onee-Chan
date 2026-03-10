package aoichan.crystal.core.registry;

import aoichan.crystal.api.effect.Effect;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class EffectRegistry {

    // [!] Code: Effect storage
    private final Map<String, Effect> effects = new HashMap<>();

    // [!] Code: Register effect
    public void register(Effect effect) {

        effects.put(effect.id(), effect);
    }

    // [!] Code: Get effect
    public Effect get(String id) {

        return effects.get(id);
    }

    // [!] Code: Get all effects
    public Collection<Effect> getAll() {

        return effects.values();
    }
} 
