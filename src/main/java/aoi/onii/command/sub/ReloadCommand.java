package vn.aoi.onii.command.sub;

import org.bukkit.command.CommandSender;
import vn.aoi.onii.command.BaseCommand;
import vn.aoi.onii.config.ConfigManager;
import vn.aoi.onii.Main;

public class ReloadCommand extends BaseCommand {

    @Override
    public String getName() {
        return "reload";
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        if (!sender.hasPermission("aoichan.admin")) {
            sender.sendMessage("§cKhông có quyền!");
            return;
        }

        ConfigManager.init(Main.getInstance());
        sender.sendMessage("§aReload thành công!");
    }
} 
