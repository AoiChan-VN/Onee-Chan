package vn.aoi.onii.command;

import org.bukkit.command.CommandSender;

public abstract class BaseCommand {
    public abstract String name();
    public abstract void run(CommandSender s,String[] a);
}
