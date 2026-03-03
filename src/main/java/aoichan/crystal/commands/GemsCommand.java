package aoichan.crystal.commands;

import aoichan.crystal.AoiMain;
import aoichan.crystal.commands.sub.*;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.*;

public class GemsCommand implements CommandExecutor {

    private final AoiMain plugin;
    private final Map<String, SubCommand> subCommands = new HashMap<>();

    public GemsCommand(AoiMain plugin) {
        this.plugin = plugin;

        register(new ReloadSub(plugin));
        register(new GiveSub(plugin));
        register(new TakeSub(plugin));
        register(new SetSub(plugin));
        register(new BalanceSub(plugin));
        register(new ListSub(plugin));
    }

    private void register(SubCommand sub) {
        subCommands.put(sub.getName().toLowerCase(), sub);
    }

    @Override
    public boolean onCommand(CommandSender sender,
                             Command command,
                             String label,
                             String[] args) {

        if (args.length == 0) {
            if (sender instanceof Player player) {
                player.openInventory(
                    aoichan.crystal.gui.GemsGUI.create(plugin)
                );
            } else {
                sender.sendMessage("Use /gems list");
            }
            return true;
        }

        SubCommand sub = subCommands.get(args[0].toLowerCase());

        if (sub == null) {
            sender.sendMessage("Unknown subcommand.");
            return true;
        }

        if (sub.isPlayerOnly() && !(sender instanceof Player)) {
            sender.sendMessage("Player only.");
            return true;
        }

        if (sub.getPermission() != null &&
            !sender.hasPermission(sub.getPermission())) {

            sender.sendMessage("No permission.");
            return true;
        }

        sub.execute(sender, Arrays.copyOfRange(args, 1, args.length));
        return true;
    }
}
