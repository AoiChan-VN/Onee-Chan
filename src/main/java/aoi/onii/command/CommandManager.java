package vn.aoi.onii.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import vn.aoi.onii.Main;
import vn.aoi.onii.command.sub.InfoCommand;
import vn.aoi.onii.command.sub.ReloadCommand;

import java.util.HashMap;

public class CommandManager implements CommandExecutor {

    private static final HashMap<String, BaseCommand> sub = new HashMap<>();

    public static void init(Main plugin) {
        CommandManager manager = new CommandManager();

        plugin.getCommand("aoi").setExecutor(manager);

        register(new ReloadCommand());
        register(new InfoCommand());
    }

    private static void register(BaseCommand cmd) {
        sub.put(cmd.getName(), cmd);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 0) return false;

        BaseCommand cmd = sub.get(args[0].toLowerCase());
        if (cmd == null) return false;

        cmd.execute(sender, args);
        return true;
    }
} 
