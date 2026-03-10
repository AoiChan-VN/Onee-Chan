package aoichan.crystal.platform.gui.forge;

import aoichan.crystal.gameplay.forge.ForgeManager;
import aoichan.crystal.api.forge.ForgeResult;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ForgeButton {

    private final ForgeManager forgeManager;

    public ForgeButton(ForgeManager forgeManager) {
        this.forgeManager = forgeManager;
    }

    // [!] Code: Click forge button
    public void click(Player player, Inventory inv) {

        ItemStack item = inv.getItem(ForgeGUI.ITEM_SLOT);
        ItemStack gem = inv.getItem(ForgeGUI.GEM_SLOT);

        if (item == null || gem == null) {

            player.sendMessage("§cMissing item or gem.");
            return;
        }

        ForgeAnimation.start(player, inv);

        ForgeResult result =
                forgeManager.forge(player, item, gem, 75);

        if (result == ForgeResult.SUCCESS) {

            player.sendMessage("§aForge success!");

        } else {

            inv.setItem(ForgeGUI.GEM_SLOT, null);

            player.sendMessage("§cForge failed!");
        }
    }
} 
