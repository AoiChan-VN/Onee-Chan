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
        sender.sendMessage("§a=== Hướng Dẫn Lệnh ===");
        sender.sendMessage("§e/aoi help §7- Show help");
        sender.sendMessage("§e/aoi profile §7→ Thông tin");
        sender.sendMessage("§e/aoi top §7→ BXH");
        sender.sendMessage("§e/aoi break §7→ Đột phá");
        
        sender.sendMessage("§c§l--- ADMIN ---");
        sender.sendMessage("§e/aoi setexp <p> <amount>");
        sender.sendMessage("§e/aoi addexp <p> <amount>");
        sender.sendMessage("§e/aoi setrealm <p> <realm>");
        sender.sendMessage("§e/aoi setstage <p> <stage>");
        sender.sendMessage("§e/aoi settech <p> <tier>");
        sender.sendMessage("§e/aoi reload");
        return true;
    }

    @Override
    public List<String> tab(CommandSender sender, String[] args) {
        return Collections.emptyList();
    }
}
 
