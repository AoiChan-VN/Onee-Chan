package vn.aoi.onii.command;

import org.bukkit.command.*;
import org.bukkit.entity.Player;
import vn.aoi.onii.classsystem.ClassManager;

public class ClassCommand implements CommandExecutor {

    private final ClassManager classManager;

    public ClassCommand(ClassManager classManager) {
        this.classManager = classManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player p)) return true;

        if (args.length == 0) {
            p.sendMessage("/class <warrior|mage>");
            return true;
        }

        classManager.setClass(p, args[0]);
        p.sendMessage("Selected class: " + args[0]);

        return true;
    }
}
 
