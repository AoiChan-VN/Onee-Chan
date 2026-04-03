package vn.aoi.onii.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import vn.aoi.onii.manager.PlayerManager;
import vn.aoi.onii.model.Cultivator;

public class CultivationCommand implements CommandExecutor {

    private final PlayerManager playerManager;

    public CultivationCommand(PlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player player)) {
            sender.sendMessage("Only player!");
            return true;
        }

        Cultivator c = playerManager.get(player.getUniqueId());

        if (c == null) {
            player.sendMessage("§cChưa load dữ liệu!");
            return true;
        }

        player.sendMessage("§6=== 【THÔNG TIN】 ===");
        player.sendMessage("§eCảnh giới: §f" + c.getRealm());
        player.sendMessage("§eTu vi: §f" + c.getLevel());
        player.sendMessage("§eLinh khí: §f" + c.getExp());

        return true;
    }
}
