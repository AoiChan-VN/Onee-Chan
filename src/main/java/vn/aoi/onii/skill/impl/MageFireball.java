package vn.aoi.onii.skill.impl;

import org.bukkit.entity.Player;
import vn.aoi.onii.classsystem.ClassContext;
import vn.aoi.onii.skill.Skill;
import vn.aoi.onii.skill.SkillScaling;
import vn.aoi.onii.combat.*;

public class MageFireball implements Skill {

    @Override
    public String getId() {
        return "fireball";
    }

    @Override
    public void execute(ClassContext ctx) {

        Player p = ctx.getPlayer();

        double damage = SkillScaling.scale(ctx.getData(), 10);

        Buff buff = new Buff("fire_power", 5000);
        buff.intel = 5;

        ctx.getData().getStats().add(StatType.INT, buff.intel);

        p.sendMessage("Fireball dmg: " + (int) damage);
    }
}
