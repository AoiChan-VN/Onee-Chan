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
        return PDCUtil.hasKey(item, "permanent_protect");
    }

    public boolean consumeProtection(ItemStack item) {

        if (PDCUtil.hasKey(item, "protection")) {
            PDCUtil.hasKey(item, "protection");
            return true;
        }
        return false;
    }

    public void applyPermanent(ItemStack item) {
        PDCUtil.setInt(item, "protection", 1);
    }

    public void applyOnce(ItemStack item) {
        PDCUtil.setInt(item, "protection", 1);
    }
}
