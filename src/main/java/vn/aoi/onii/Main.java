package vn.aoi.onii;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;

public final class Main extends JavaPlugin {

    private static Main instance;
    private FileConfiguration config;

    public static Main getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();
        config = getConfig();

        Bukkit.getLogger().info("[OniiCore] Plugin Enabled!");
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info("[OniiCore] Plugin Disabled!");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!command.getName().equalsIgnoreCase("onii")) return false;

        if (!sender.hasPermission("onii.admin")) {
            sender.sendMessage(color("&cNo permission."));
            return true;
        }

        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            reloadAsync(sender);
            return true;
        }

        sender.sendMessage(color("&eUsage: /onii reload"));
        return true;
    }

    private void reloadAsync(CommandSender sender) {
        CompletableFuture.runAsync(() -> {
            try {
                reloadConfig();
                config = getConfig();
            } catch (Exception e) {
                Bukkit.getLogger().log(Level.SEVERE, "Reload failed", e);
            }
        }).thenRun(() -> sender.sendMessage(color(config.getString("messages.reload"))));
    }

    public String color(String msg) {
        return msg.replace("&", "§");
    }
}
