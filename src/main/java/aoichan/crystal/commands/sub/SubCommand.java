package aoichan.crystal.commands.sub;

import org.bukkit.command.CommandSender;

public interface SubCommand {

    String getName();

    String getPermission();

    boolean isPlayerOnly();

    void execute(CommandSender sender, String[] args);
} 
