package aoichan.crystal.commands;

import aoichan.crystal.AoiMain;
import aoichan.crystal.core.ReloadManager;
import aoichan.crystal.gui.GemsGUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
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

    // ===== Console Mode =====
    if (!(sender instanceof Player player)) {

        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            ReloadManager.reload(plugin);
            sender.sendMessage("Gems reloaded.");
            return true;
        }

        sender.sendMessage("Console usage: /gems reload");
        return true;
    }

    // ===== Player Mode =====
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
