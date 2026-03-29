package vn.aoi.onii.commands.sub;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import vn.aoi.onii.Main;
import vn.aoi.onii.commands.core.AbstractCommand;
import vn.aoi.onii.player.PlayerData;

import java.util.Collections;
import java.util.List;

public class InfoCommand extends AbstractCommand {

    @Override
    public String getName() {
        return "info";
    }

    @Override
    public String getDescription() {
        return "Xem thông tin tu tiên";
    }

    @Override
    public String getUsage() {
        return "/aoi info";
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {

        if (!(sender instanceof Player player)) {
            sender.sendMessage("Chỉ player dùng được!");
            return true;
        }

        PlayerData data = Main.getInstance()
                .getPlayerManager()
                .getPlayer(player.getUniqueId());

        sender.sendMessage("§6═══════ 《 THÔNG TIN 》 ═══════");
        sender.sendMessage("§6═══════════════════════════");

        return true;
    }

    @Override
    public List<String> tab(CommandSender sender, String[] args) {
        return Collections.emptyList();
    }
}
