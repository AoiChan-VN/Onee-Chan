package aoichan.crystal.platform.gui;

import aoichan.crystal.gameplay.gem.Gem;
import aoichan.crystal.gameplay.gem.GemManager;
import aoichan.crystal.infrastructure.broadcast.BroadcastManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class GemForgeGUI {

    public static void open(Player player) {

        // 【!】Code: GUI ép ngọc
        Inventory inv = Bukkit.createInventory(
                null,
                27,
                "Gem Forge"
        );

        player.openInventory(inv);

    }

    public static void forge(Player player, String gemId) {

        Gem gem = GemManager.getGem(gemId);

        if (gem == null) return;

        // 【!】Code: broadcast thiên kiếp
        BroadcastManager.broadcastTribulation(player, gem);

        // 【!】Code: giả lập thành công
        boolean success = true;

        if (success) {

            BroadcastManager.successBell(player);

            player.sendMessage("§aÉp ngọc thành công: " + gem.getName());

        } else {

            player.sendMessage("§cÉp ngọc thất bại!");

        }

    }

} 
