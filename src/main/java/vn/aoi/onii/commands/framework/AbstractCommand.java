package vn.aoi.onii.commands.framework;

import vn.aoi.onii.Main;
import vn.aoi.onii.config.MessageManager;

public abstract class AbstractCommand implements ICommandModule {

    protected final Main plugin;

    public AbstractCommand(Main plugin) {
        this.plugin = plugin;
    }

    protected boolean checkCooldown(org.bukkit.command.CommandSender sender,
                                    String command,
                                    int seconds) {

        String key = sender.getName() + ":" + command;

        if (CooldownManager.isOnCooldown(key, seconds)) {
            long remain = CooldownManager.getRemaining(key, seconds);
            sender.sendMessage(MessageManager.get("cooldown")
                    .replace("{time}", String.valueOf(remain)));
            return false;
        }

        CooldownManager.setCooldown(key);
        return true;
    }
} 
