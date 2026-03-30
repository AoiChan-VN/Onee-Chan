package vn.aoi.onii.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import vn.aoi.onii.player.PlayerManager;
import vn.aoi.onii.cultivation.CultivationService;

public class TutienCommand implements CommandExecutor {

    private final PlayerManager manager;
    private final CultivationService service;

    public TutienCommand(PlayerManager manager, CultivationService service) {
        this.manager = manager;
        this.service = service;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player p)) return true;

        var data = manager.get(p.getUniqueId());

        if (args.length == 1 && args[0].equalsIgnoreCase("tuvi")) {
            service.addPower(data, 50);
            p.sendMessage("§a+50 Tu Vi | Stage: " + data.getStage());
            return true;
        }

        p.sendMessage("§e/aoi tuvi");
        return true;
    }
} 
