package vn.aoi.onii.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import vn.aoi.onii.player.PlayerData;
import vn.aoi.onii.player.PlayerManager;
import vn.aoi.onii.shop.ShopManager;

public class AoiCommand implements CommandExecutor {

    private final PlayerManager manager;

    public AoiCommand(PlayerManager manager) {
        this.manager = manager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player player)) return true;

        if (args.length == 1 && args[0].equalsIgnoreCase("shop")) {
            player.openInventory(new ShopManager().createShop());
            return true;
        }

        if (args.length == 2 && args[0].equalsIgnoreCase("info")) {

            Player target = Bukkit.getPlayer(args[1]);
            if (target == null) return true;

            PlayerData data = manager.get(target.getUniqueId(), target.getName());

            sender.sendMessage("§6Đạo hữu: §f" + data.getName());
            sender.sendMessage("§6Cảnh giới: §f" + data.getRealm().getDisplay());
            sender.sendMessage("§6Tu vi: §f" + data.getStage().getDisplay());
            sender.sendMessage("§6Tông môn: §f" + data.getSect());

            return true;
        }

        player.sendMessage("/aoi shop | /aoi info <player>");
        return true;
    }
}
