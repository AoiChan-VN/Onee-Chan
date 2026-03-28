package vn.aoi.onii.weapon;

import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.*;
import vn.aoi.onii.enums.WeaponTier;
import vn.aoi.onii.nbt.NBTManager;

import java.util.Random;

public class WeaponEffectListener implements Listener {

    private final Random rand = new Random();

    @EventHandler
    public void onHit(EntityDamageByEntityEvent e) {

        if (!(e.getDamager() instanceof Player p)) return;
        if (!(e.getEntity() instanceof LivingEntity target)) return;

        ItemStack item = p.getInventory().getItemInMainHand();

        String tierStr = NBTManager.get(item, "tier");
        String effects = NBTManager.get(item, "effects");

        if (tierStr == null || effects == null) return;

        WeaponTier tier = WeaponTier.fromString(tierStr);
        int bonus = tier.getLevel() * 5;

        for (String effect : effects.split(",")) {

            switch (effect.toUpperCase()) {

                case "LIFESTEAL":
                    if (rand.nextInt(100) < 20 + bonus) {
                        p.setHealth(Math.min(p.getHealth() + 2 + tier.getLevel(), p.getMaxHealth()));
                    }
                    break;

                case "BURN":
                    if (rand.nextInt(100) < 20 + bonus) {
                        target.setFireTicks(40 + tier.getLevel() * 20);
                    }
                    break;

                case "CRIT":
                    if (rand.nextInt(100) < 15 + bonus) {
                        e.setDamage(e.getDamage() * (1.3 + tier.getLevel() * 0.1));
                    }
                    break;

                case "LIGHTNING":
                    if (rand.nextInt(100) < 5 + tier.getLevel()) {
                        target.getWorld().strikeLightning(target.getLocation());
                    }
                    break;
            }
        }
    }
}
