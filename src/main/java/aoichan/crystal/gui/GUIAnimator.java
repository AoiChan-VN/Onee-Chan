package aoichan.crystal.gui;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

public class GUIAnimator {

    public static void animate(Inventory inv) {

        Bukkit.getScheduler().runTaskTimer(
            Bukkit.getPluginManager().getPlugin("GemsUltimate"),
            new Runnable() {

                int slot = 0;

                @Override
                public void run() {
                    if (slot >= inv.getSize()) slot = 0;
                    inv.setItem(slot, null);
                    slot++;
                }
            }, 0L, 2L);
    }
}
