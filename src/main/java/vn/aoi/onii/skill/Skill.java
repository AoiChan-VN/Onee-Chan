package vn.aoi.onii.skill;

import org.bukkit.entity.Player;
import vn.aoi.onii.classsystem.ClassContext;

public interface Skill {

    String getId();

    long getCooldown(); // ms

    void execute(ClassContext ctx);
}
