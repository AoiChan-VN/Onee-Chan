package vn.aoi.onii.combat.listener;

import org.bukkit.entity.Player;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import vn.aoi.onii.player.PlayerManager;
import vn.aoi.onii.combat.*;

public class CombatListener implements Listener {

    private final PlayerManager playerManager;
    private final BuffManager buffManager;

    public CombatListener(PlayerManager playerManager, BuffManager buffManager) {
        this.playerManager = playerManager;
        this.buffManager = buffManager;
    }

    @EventHandler
    public void onHit(EntityDamageByEntityEvent e) {

        if (!(e.getDamager() instanceof Player attacker)) return;
        if (!(e.getEntity() instanceof LivingEntity target)) return;

        var attackerData = playerManager.get(attacker);
        if (attackerData == null) return;

        StatProfile atk = buffManager.apply(attacker.getUniqueId(), attackerData.getStats());
        ElementProfile atkElem = attackerData.getElements();

        StatProfile def = new StatProfile();
        ElementProfile defElem = new ElementProfile();

        if (target instanceof Player tp) {
            var d = playerManager.get(tp);
            if (d != null) {
                def = buffManager.apply(tp.getUniqueId(), d.getStats());
                defElem = d.getElements();
            }
        }

        DamageResult result = DamageEngine.calculate(
                atk,
                def,
                atkElem,
                defElem,
                e.getDamage(),
                DamageType.PHYSICAL
        );

        e.setDamage(result.damage);

        if (result.crit) attacker.sendMessage("CRIT " + (int) result.damage);
        if (result.element != ElementType.NONE) {
            attacker.sendMessage("ELEMENT " + result.element.name());
        }
    }
} 
