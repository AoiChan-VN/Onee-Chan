package aoichan.crystal.commands.sub;

import aoichan.crystal.AoiMain;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GiveSub implements SubCommand {

    private final AoiMain plugin;

    public GiveSub(AoiMain plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getName() {
        return "give";
    }

    @Override
    public String getPermission() {
        return "gems.give";
    }

    @Override
    public boolean isPlayerOnly() {
        return false;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        if (args.length < 3) {
            sender.sendMessage("Usage: /gems give <player> <type> <amount>");
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

        plugin.getGemsManager().addGem(target.getUniqueId(), type, amount);

        sender.sendMessage("Gave " + amount + " " + type + " to " + target.getName());
        target.sendMessage("You received " + amount + " " + type);
    }
} 
