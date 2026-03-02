package aoichan.crystal.core;

import aoichan.crystal.AoiMain;
import aoichan.crystal.utils.PDCUtil;
import org.bukkit.inventory.ItemStack;

public class ProtectionManager {

    private final AoiMain plugin;

    public ProtectionManager(AoiMain plugin) {
        this.plugin = plugin;
    }

    public boolean isPermanentProtected(ItemStack item) {
        return PDCUtil.has(item, "permanent_protect");
    }

    public boolean consumeProtection(ItemStack item) {

        if (PDCUtil.has(item, "protect_once")) {
            PDCUtil.remove(item, "protect_once");
            return true;
        }
        return false;
    }

    public void applyPermanent(ItemStack item) {
        PDCUtil.set(item, "permanent_protect", 1);
    }

    public void applyOnce(ItemStack item) {
        PDCUtil.set(item, "protect_once", 1);
    }
}
