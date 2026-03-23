package vn.aoi.onii.skill;

import org.bukkit.entity.Player;
import vn.aoi.onii.classsystem.*;
import vn.aoi.onii.player.PlayerManager;

public class SkillManager {

    private final SkillRegistry registry;
    private final CooldownManager cooldown;
    private final ClassManager classManager;
    private final PlayerManager playerManager;

    public SkillManager(SkillRegistry registry, CooldownManager cooldown,
                        ClassManager classManager, PlayerManager playerManager) {
        this.registry = registry;
        this.cooldown = cooldown;
        this.classManager = classManager;
        this.playerManager = playerManager;
    }

    public void use(Player player, String skillId) {
        Skill skill = registry.get(skillId);
        if (skill == null) return;

        if (cooldown.isOnCooldown(player.getUniqueId(), skillId)) {
            player.sendMessage("Skill on cooldown!");
            return;
        }

        var data = playerManager.get(player);
        if (data == null) return;

        ClassContext ctx = new ClassContext(player, data);

        skill.execute(ctx);

        cooldown.setCooldown(player.getUniqueId(), skillId, skill.getCooldown());
    }
}
