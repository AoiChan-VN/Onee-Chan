package aoichan.crystal.platform.command.sub;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GiveCommand implements SubCommand {

    @Override
    public void execute(CommandSender sender, String[] args) {

        if (args.length < 4) {

            sender.sendMessage("/crystal give <player> <gem> <level>");
            return;
        }

        Player target = Bukkit.getPlayer(args[1]);

        if (target == null) {

            sender.sendMessage("Player not found.");
            return;
        }

        String gemId = args[2];
        int level = Integer.parseInt(args[3]);

        // [!] Code: Create gem item
        ItemStack gem =
                GemFactory.create(gemId, level);

        target.getInventory().addItem(gem);
        
        sender.sendMessage("Gem given.");
    }
} 
