package aoichan.crystal.platform.command;

import aoichan.crystal.platform.command.sub.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.HashMap;
import java.util.Map;

public class CrystalCommand implements CommandExecutor {

    private final Map<String, SubCommand> commands = new HashMap<>();

    public CrystalCommand() {

        // [!] Code: Register subcommands
        commands.put("help", new HelpCommand());
        commands.put("forge", new ForgeCommand());
        commands.put("give", new GiveCommand());
        commands.put("socket", new SocketCommand());
        commands.put("reload", new ReloadCommand());
    }

    @Override
    public boolean onCommand(CommandSender sender,
                             Command command,
                             String label,
                             String[] args) {

        if (args.length == 0) {

            commands.get("help").execute(sender, args);
            return true;
        }

        SubCommand sub = commands.get(args[0].toLowerCase());

        if (sub == null) {

            sender.sendMessage("§cUnknown command.");
            return true;
        }

        sub.execute(sender, args);

        return true;
    }
}
