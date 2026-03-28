package vn.aoi.onii.commands.sub;

import org.bukkit.command.CommandSender;
import vn.aoi.onii.commands.core.SubCommand;

import java.util.Collections;
import java.util.List;

public class HelpCommand implements SubCommand {

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getDescription() {
        return "Show help";
    }

    @Override
    public String getUsage() {
        return "/aoi help";
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        sender.sendMessage("§a=== AOI COMMANDS ===");
        sender.sendMessage("§e/aoi help §7- Show help");
        return true;
    }

    @Override
    public List<String> tab(CommandSender sender, String[] args) {
        return Collections.emptyList();
    }
}
 
