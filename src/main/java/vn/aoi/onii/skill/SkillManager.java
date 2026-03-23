package vn.aoi.onii.skill;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import vn.aoi.onii.Main;
import vn.aoi.onii.classsystem.ClassContext;
import vn.aoi.onii.classsystem.ClassManager;
import vn.aoi.onii.player.PlayerManager;
import vn.aoi.onii.skill.config.SkillConfig;
import vn.aoi.onii.skill.config.SkillConfigManager;

public class SkillManager {

    private final Main plugin;
    private final SkillRegistry registry;
    private final CooldownManager cooldown;
    private final ClassManager classManager;
    private final PlayerManager playerManager;
    private final SkillConfigManager configManager;

    public SkillManager(Main plugin, SkillRegistry registry, CooldownManager cooldown,
                        ClassManager classManager, PlayerManager playerManager,
                        SkillConfigManager configManager) {
        this.plugin = plugin;
        this.registry = registry;
        this.cooldown = cooldown;
        this.classManager = classManager;
        this.playerManager = playerManager;
        this.configManager = configManager;
    }

    public void use(Player player, String skillId) {

        if (!Bukkit.isPrimaryThread()) {
            Bukkit.getScheduler().runTask(plugin, () -> use(player, skillId));
            return;
        }

        Skill skill = registry.get(skillId);
        if (skill == null) return;

        SkillConfig cfg = configManager.get(skillId);
        if (cfg == null) return;

        if (cooldown.isOnCooldown(player.getUniqueId(), skillId)) {
            player.sendMessage("Cooldown...");
            return;
        }

        var data = playerManager.get(player);
        if (data == null) return;

        if (!data.hasMana(cfg.mana)) {
            player.sendMessage("Not enough mana");
            return;
        }

        data.consumeMana(cfg.mana);

        skill.execute(new ClassContext(player, data));

        cooldown.setCooldown(player.getUniqueId(), skillId, cfg.cooldown);
    }
}
