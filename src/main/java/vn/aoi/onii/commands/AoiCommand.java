package vn.aoi.onii.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import vn.aoi.onii.commands.confirm.ConfirmManager;
import vn.aoi.onii.commands.cooldown.CommandCooldown;
import vn.aoi.onii.service.CultivationService;
import vn.aoi.onii.manager.PlayerManager;
import vn.aoi.onii.model.Cultivator;

@CommandAlias("aoi")
public class AoiCommand extends BaseCommand {

    private final PlayerManager playerManager;
    private final CultivationService service;

    public AoiCommand(PlayerManager playerManager, CultivationService service) {
        this.playerManager = playerManager;
        this.service = service;
    }

    @Subcommand("info")
    public void onInfo(Player player) {

        Cultivator c = playerManager.get(player.getUniqueId());

        if (c == null) {
            player.sendMessage("§cChưa load!");
            return;
        }

        player.sendMessage("§6=== TU VI ===");
        player.sendMessage("§eCảnh giới: §f" + c.getRealm());
        player.sendMessage("§eLevel: §f" + c.getLevel());
        player.sendMessage("§eEXP: §f" + c.getExp());
    }

    @Subcommand("addexp")
    @CommandPermission("aoi.admin")
    public void onAddExp(CommandSender sender, Player target, double amount) {

        if (sender instanceof Player player) {
            if (CommandCooldown.isOnCooldown("addexp", player.getUniqueId(), 3000)) {
                player.sendMessage("§cCooldown!");
                return;
            }
        }

        service.addExp(target, amount);
        sender.sendMessage("§aOK");
    }

    @Subcommand("setrealm")
    @CommandPermission("aoi.admin")
    public void onSetRealm(CommandSender sender, OfflinePlayer target, String realm) {

        if (!service.getRealmManager().exists(realm)) {
            sender.sendMessage("§cRealm không tồn tại!");
            return;
        }

        var c = playerManager.get(target.getUniqueId());
        if (c != null) {
            c.setRealm(realm);
            c.setLevel(1);
            c.setExp(0);
        }

        sender.sendMessage("§aOK");
    }

    @Subcommand("reset")
    public void onReset(CommandSender sender, OfflinePlayer target) {

        if (!(sender instanceof Player player)) {
            reset(target);
            return;
        }

        ConfirmManager.request(player.getUniqueId(), () -> reset(target));
        player.sendMessage("§c/aoi confirm");
    }

    @Subcommand("confirm")
    public void onConfirm(Player player) {

        if (!ConfirmManager.confirm(player.getUniqueId())) {
            player.sendMessage("§cHết hạn!");
        } else {
            player.sendMessage("§aOK");
        }
    }

    private void reset(OfflinePlayer target) {
        var c = playerManager.get(target.getUniqueId());
        if (c != null) {
            c.setRealm("Phàm nhân");
            c.setLevel(1);
            c.setExp(0);
        }
    }
}
