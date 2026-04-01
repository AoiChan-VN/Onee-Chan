package vn.aoi.onii.command.sub;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import vn.aoi.onii.command.BaseCommand;
import vn.aoi.onii.data.PlayerData;
import vn.aoi.onii.manager.PlayerManager;

public class InfoCommand extends BaseCommand {

    @Override
    public String getName() {
        return "info";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        if (!(sender instanceof Player player)) return;

        PlayerData data = PlayerManager.get(player);

        player.sendMessage("§6Cảnh giới: §e" + data.getRealm());
        player.sendMessage("§6Tầng: §e" + data.getLevel());
        player.sendMessage("§6Tu vi: §e" + data.getExp());
    }
} 
