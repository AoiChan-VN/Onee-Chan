package vn.aoi.onii.command;

import org.bukkit.command.*;
import org.bukkit.entity.Player;
import vn.aoi.onii.player.PlayerData;
import vn.aoi.onii.player.PlayerManager;

public class RootCommand implements CommandExecutor {

    private final PlayerManager pm;

    public RootCommand(PlayerManager pm) {
        this.pm = pm;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player p)) return true;

        PlayerData data = pm.get(p);

        if (args.length == 0) {
            p.sendMessage("Level: " + data.getLevel());
            return true;
        }

        if (args[0].equalsIgnoreCase("addexp")) {
            data.addExp(50);
            p.sendMessage("+50 EXP");
        }

        return true;
    }
}
