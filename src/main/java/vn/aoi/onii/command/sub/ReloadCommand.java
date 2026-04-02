package vn.aoi.onii.command.sub;

import org.bukkit.command.CommandSender;
import vn.aoi.onii.command.BaseCommand;
import vn.aoi.onii.config.ConfigManager;
import vn.aoi.onii.Main;

public class ReloadCommand extends BaseCommand {

    public String name(){return "reload";}

    public void run(CommandSender s,String[] a){
        ConfigManager.init(Main.get());
        s.sendMessage("Reload xong");
    }
} 
