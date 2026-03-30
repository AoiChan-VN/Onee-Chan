package vn.aoi.onii.command;

import org.bukkit.command.*;
import org.bukkit.entity.Player;
import vn.aoi.onii.util.RankUtil;

public class CultivationCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player player)) return true;

        player.sendMessage("§eCảnh giới: §6" +
                RankUtil.RANKS[0] + " §e- §b" +
                RankUtil.PHASES[0]);

        return true;
    }
} 
