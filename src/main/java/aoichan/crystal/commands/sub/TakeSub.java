package aoichan.crystal.commands.sub;

import aoichan.crystal.AoiMain;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TakeSub implements SubCommand {

    private final AoiMain plugin;

    public TakeSub(AoiMain plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getName() {
        return "take";
    }

    @Override
    public String getPermission() {
        return "gems.take";
    }

    @Override
    public boolean isPlayerOnly() {
        return false;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        if (args.length < 3) {
            sender.sendMessage("Usage: /gems take <player> <type> <amount>");
            return;
        }

        Player target = Bukkit.getPlayerExact(args[0]);
        if (target == null) {
            sender.sendMessage("Player not found.");
            return;
        }

        String type = args[1];

        int amount;
        try {
            amount = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            sender.sendMessage("Amount must be a number.");
            return;
        }

        plugin.getGemsManager().removeGem(target.getUniqueId(), type, amount);

        sender.sendMessage("Removed " + amount + " " + type + " from " + target.getName());
    }
} 
