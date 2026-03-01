package aoichan.crystal.commands;

import aoichan.crystal.AoiMain;
import aoichan.crystal.core.ReloadManager;
import aoichan.crystal.gui.GemsGUI;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class GemsCommand implements CommandExecutor {

    private final AoiMain plugin;

    public GemsCommand(AoiMain plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender,
                             Command command,
                             String label,
                             String[] args) {

        if (!(sender instanceof Player player)) {
            sender.sendMessage("Player only.");
            return true;
        }

        if (args.length == 0) {
            player.openInventory(GemsGUI.create(plugin));
            return true;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            if (!player.hasPermission("gems.admin")) {
                player.sendMessage("No permission.");
                return true;
            }

            ReloadManager.reload(plugin);
            player.sendMessage("Gems reloaded.");
            return true;
        }

        return true;
    }
}
