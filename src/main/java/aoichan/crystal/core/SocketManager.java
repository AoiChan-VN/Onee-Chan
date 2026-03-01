package aoichan.crystal.core;

import aoichan.crystal.utils.PDCUtil;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class SocketManager {

    private static final int MAX_SLOTS = 3;

    public void attachGem(ItemStack item, String gemId) {

        List<String> gems = PDCUtil.getSocketList(item);

        if (gems.size() >= MAX_SLOTS) return;

        gems.add(gemId);
        PDCUtil.setSocketList(item, gems);
    }

    public List<String> getGems(ItemStack item) {
        return PDCUtil.getSocketList(item);
    }
}
