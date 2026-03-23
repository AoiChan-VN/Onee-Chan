package vn.aoi.onii.skill.impl;

import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.*;
import vn.aoi.onii.skill.Skill;
import vn.aoi.onii.classsystem.ClassContext;

public class WarriorSlash implements Skill {

    @Override
    public String getId() { return "slash"; }

    @Override
    public long getCooldown() { return 3000; }

    @Override
    public void execute(ClassContext ctx) {
        Player p = ctx.getPlayer();

        for (Entity e : p.getNearbyEntities(3,3,3)) {
            if (e instanceof LivingEntity le && le != p) {
                le.damage(6.0, p);
            }
        }

        p.getWorld().spawnParticle(Particle.SWEEP_ATTACK, p.getLocation(), 10);
        p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_ATTACK_SWEEP, 1, 1);
    }
}
