package vn.aoi.onii.weapon;

import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.*;
import vn.aoi.onii.nbt.NBTManager;

import java.util.Random;

public class WeaponEffectListener implements Listener {

    private final Random rand = new Random();

    @EventHandler
    public void onHit(EntityDamageByEntityEvent e) {

        if (!(e.getDamager() instanceof Player p)) return;
        if (!(e.getEntity() instanceof LivingEntity target)) return;

        ItemStack item = p.getInventory().getItemInMainHand();

        String effects = NBTManager.get(item, "effects");
        if (effects == null) return;

        for (String effect : effects.split(",")) {

            switch (effect.toUpperCase()) {

                case "LIFESTEAL":
                    if (rand.nextInt(100) < 25) {
                        p.setHealth(Math.min(p.getHealth() + 2, p.getMaxHealth()));
                    }
                    break;

                case "BURN":
                    if (rand.nextInt(100) < 30) {
                        target.setFireTicks(60);
                    }
                    break;

                case "FREEZE":
                    if (rand.nextInt(100) < 25) {
                        target.addPotionEffect(new PotionEffect(
                                PotionEffectType.SLOW, 60, 2));
                    }
                    break;

                case "POISON":
                    if (rand.nextInt(100) < 25) {
                        target.addPotionEffect(new PotionEffect(
                                PotionEffectType.POISON, 60, 1));
                    }
                    break;

                case "CRIT":
                    if (rand.nextInt(100) < 20) {
                        e.setDamage(e.getDamage() * 1.5);
                    }
                    break;

                case "LIGHTNING":
                    if (rand.nextInt(100) < 10) {
                        target.getWorld().strikeLightning(target.getLocation());
                    }
                    break;

                case "WEAKNESS":
                    if (rand.nextInt(100) < 30) {
                        target.addPotionEffect(new PotionEffect(
                                PotionEffectType.WEAKNESS, 80, 1));
                    }
                    break;
            }
        }
    }
} 
