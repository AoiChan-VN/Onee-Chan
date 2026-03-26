package vn.aoi.onii.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import vn.aoi.onii.player.PlayerData;
import vn.aoi.onii.player.PlayerManager;

public class AoiCommand implements CommandExecutor {

    private final PlayerManager manager;

    public AoiCommand(PlayerManager manager) {
        this.manager = manager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (args.length == 2 && args[0].equalsIgnoreCase("info")) {

            Player target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                sender.sendMessage("Không tìm thấy người chơi");
                return true;
            }

            PlayerData data = manager.getPlayer(target.getUniqueId(), target.getName());

            sender.sendMessage("§6Đạo hữu: §f" + data.getName());
            sender.sendMessage("§6Cảnh giới: §f" + data.getCanhGioi());
            sender.sendMessage("§6Tu vi: §f" + data.getTuVi());
            sender.sendMessage("§6Tông môn: §7(Chưa có)");

            return true;
        }

        sender.sendMessage("/aoi info <player>");
        return true;
    }
}
