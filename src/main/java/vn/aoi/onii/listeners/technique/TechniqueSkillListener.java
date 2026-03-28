package vn.aoi.onii.listeners.technique;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.*;
import vn.aoi.onii.player.PlayerManager;

import java.util.*;

public class TechniqueSkillListener implements Listener {

    private final PlayerManager manager;
    private final Map<UUID, Long> cd = new HashMap<>();

    public TechniqueSkillListener(PlayerManager manager) {
        this.manager = manager;
    }

    @EventHandler
    public void use(PlayerInteractEvent e) {

        if (!e.getAction().toString().contains("RIGHT_CLICK")) return;

        Player p = e.getPlayer();
        var data = manager.get(p.getUniqueId(), p.getName());

        String tech = data.getTechnique();

        long now = System.currentTimeMillis();

        if (cd.containsKey(p.getUniqueId()) && now - cd.get(p.getUniqueId()) < 5000) {
            p.sendMessage("§cHồi chiêu...");
            return;
        }

        cd.put(p.getUniqueId(), now);

        switch (tech.toUpperCase()) {

            case "LINH":
                p.setHealth(Math.min(p.getHealth() + 2, p.getMaxHealth()));
                break;

            case "HUYEN":
                p.addPotionEffect(new PotionEffect(
                        PotionEffectType.getByName("INCREASE_DAMAGE"), 100, 1));
                break;

            case "DIA":
                p.getWorld().createExplosion(p.getLocation(), 1, false, false);
                break;

            case "THIEN":
                p.addPotionEffect(new PotionEffect(
                        PotionEffectType.getByName("REGENERATION"), 100, 2));
                break;

            case "THAN":
                p.setVelocity(p.getLocation().getDirection().multiply(2));
                break;
        }
    }
} 
