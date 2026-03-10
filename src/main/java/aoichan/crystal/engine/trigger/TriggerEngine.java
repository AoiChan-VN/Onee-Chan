package aoichan.crystal.engine.trigger;

import aoichan.crystal.api.effect.TriggerType;
import aoichan.crystal.engine.effect.EffectEngine;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class TriggerEngine {

    // [!] Code: Effect engine
    private final EffectEngine effectEngine;

    public TriggerEngine(EffectEngine effectEngine) {
        this.effectEngine = effectEngine;
    }

    // [!] Code: Fire trigger
    public void fire(TriggerType trigger, Player player, LivingEntity target) {

        effectEngine.trigger(trigger, player, target);
    }
}
