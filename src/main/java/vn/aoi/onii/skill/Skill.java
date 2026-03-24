package vn.aoi.onii.skill;

import org.bukkit.entity.Player;
import vn.aoi.onii.classsystem.ClassContext;

public interface Skill {

    String getId();

    void execute(ClassContext ctx);
}
