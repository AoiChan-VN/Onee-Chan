package aoichan.crystal.platform.listener;

import aoichan.crystal.gameplay.effect.EffectEngine;
import aoichan.crystal.gameplay.effect.GemEffect;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EffectTriggerListener implements Listener {

    @EventHandler
    public void onAttack(EntityDamageByEntityEvent event) {

        if (!(event.getDamager() instanceof Player player))
            return;

        if (!(event.getEntity() instanceof LivingEntity target))
            return;

        // [!] Code: test effect
        GemEffect effect =
                new GemEffect(
                        aoichan.crystal.gameplay.effect.EffectType.LIGHTNING,
                        0.2,
                        1
                );

        EffectEngine.trigger(
                player,
                target,
                effect
        );
    }
} 
