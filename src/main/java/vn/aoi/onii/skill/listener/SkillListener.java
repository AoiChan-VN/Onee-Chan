package vn.aoi.onii.skill.listener;

import org.bukkit.event.*;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.block.Action;
import org.bukkit.entity.Player;

import vn.aoi.onii.skill.SkillManager;
import vn.aoi.onii.classsystem.ClassManager;
import vn.aoi.onii.classsystem.PlayerClass;

public class SkillListener implements Listener {

    private final SkillManager skillManager;
    private final ClassManager classManager;

    public SkillListener(SkillManager sm, ClassManager cm) {
        this.skillManager = sm;
        this.classManager = cm;
    }

    @EventHandler
    public void onUse(PlayerInteractEvent e) {
        if (e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_BLOCK) return;

        Player p = e.getPlayer();

        PlayerClass clazz = classManager.getClass(p.getUniqueId());
        if (clazz == null) return;

        //【❅】 Map skill theo class
        switch (clazz.getId()) {
            case "warrior" -> skillManager.use(p, "slash");
            case "mage" -> skillManager.use(p, "fireball");
        }
    }
}
