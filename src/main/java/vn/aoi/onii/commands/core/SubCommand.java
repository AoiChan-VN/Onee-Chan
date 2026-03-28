package vn.aoi.onii.commands.core;

import org.bukkit.command.CommandSender;

import java.util.List;

public interface SubCommand {
    String getName();
    String getDescription();
    String getUsage();

    boolean execute(CommandSender sender, String[] args);

    List<String> tab(CommandSender sender, String[] args);
} 
