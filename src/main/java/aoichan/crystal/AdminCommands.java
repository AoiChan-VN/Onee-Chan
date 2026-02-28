package aoidev.crystal;

import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.concurrent.CompletableFuture;

/**
 * Basic admin command executor - /gems reload|give|list|info
 */
public class AdminCommands implements CommandExecutor, TabCompleter {

    private final Main plugin;
    private final GemManager gemManager;
    private final StorageManager storage;

    public AdminCommands(Main plugin, GemManager gemManager, StorageManager storage) {
        this.plugin = plugin;
        this.gemManager = gemManager;
        this.storage = storage;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("gems.admin")) {
            sender.sendMessage("§cYou don't have permission.");
            return true;
        }
        if (args.length == 0) {
            sender.sendMessage("§e/gems reload|give <player> <type> <level>|list|info <id>");
            return true;
        }
        switch (args[0].toLowerCase(Locale.ROOT)) {
            case "reload":
                plugin.reloadConfig();
                sender.sendMessage("§aConfig reloaded.");
                break;
            case "give":
                if (args.length < 4) {
                    sender.sendMessage("§c/gems give <player> <type> <level>");
                    return true;
                }
                Player target = Bukkit.getPlayer(args[1]);
                if (target == null) {
                    sender.sendMessage("§cPlayer not found.");
                    return true;
                }
                String type = args[2];
                int level;
                try { level = Integer.parseInt(args[3]); } catch (NumberFormatException ex) {
                    sender.sendMessage("§cLevel must be a number.");
                    return true;
                }
                gemManager.createAndSaveGemAsync(type, level).thenRun(() -> {
                    target.sendMessage("§bYou received a gem: " + type + " §7(level " + level + ")");
                    sender.sendMessage("§aCreated gem for " + target.getName());
                });
                break;
            case "list":
                sender.sendMessage("§aGems loaded: " + gemManager.getAllGems().size());
                break;
            case "info":
                if (args.length < 2) {
                    sender.sendMessage("§c/gems info <uuid>");
                    return true;
                }
                try {
                    UUID id = UUID.fromString(args[1]);
                    gemManager.getGem(id).ifPresentOrElse(g -> sender.sendMessage("§aGem: " + g.getType() + " lvl " + g.getLevel()), () -> sender.sendMessage("§cNot found."));
                } catch (IllegalArgumentException e) {
                    sender.sendMessage("§cInvalid UUID.");
                }
                break;
            default:
                sender.sendMessage("§cUnknown subcommand.");
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) return Arrays.asList("reload", "give", "list", "info");
        return Collections.emptyList();
    }
                      }
