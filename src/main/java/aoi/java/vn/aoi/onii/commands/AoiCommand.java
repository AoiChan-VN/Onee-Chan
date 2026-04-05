package vn.aoi.onii.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import vn.aoi.onii.commands.confirm.ConfirmManager;
import vn.aoi.onii.commands.cooldown.CommandCooldown;
import vn.aoi.onii.manager.CultivationService;
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

    // ================= PLAYER =================

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

    // ================= ADMIN =================

    @Subcommand("addexp")
    @CommandPermission("aoi.admin")
    @CommandCompletion("@players @range:1-100000")
    public void onAddExp(CommandSender sender, Player target, double amount) {

        if (sender instanceof Player player) {
            if (CommandCooldown.isOnCooldown("addexp", player.getUniqueId(), 3000)) {
                long remain = CommandCooldown.getRemaining("addexp", player.getUniqueId(), 3000);
                player.sendMessage("§cCooldown: " + (remain / 1000.0) + "s");
                return;
            }
        }

        service.addExp(target, amount);
        sender.sendMessage("§aĐã thêm EXP!");
    }

    @Subcommand("setexp")
    @CommandPermission("aoi.admin")
    public void onSetExp(CommandSender sender, OfflinePlayer target, double amount) {

        var c = playerManager.get(target.getUniqueId());
        if (c != null) {
            c.setExp(amount);
        }

        sender.sendMessage("§aSet EXP!");
    }

    @Subcommand("setrealm")
    @CommandPermission("aoi.admin")
    @CommandCompletion("@players @realms")
    public void onSetRealm(CommandSender sender, OfflinePlayer target, String realm) {

        var c = playerManager.get(target.getUniqueId());
        if (c != null) {
            c.setRealm(realm);
            c.setLevel(1);
            c.setExp(0);
        }

        sender.sendMessage("§aSet Realm!");
    }

    @Subcommand("reset")
    @CommandPermission("aoi.admin")
    public void onReset(CommandSender sender, OfflinePlayer target) {

        if (!(sender instanceof Player player)) {
            reset(target);
            sender.sendMessage("§aReset (console)");
            return;
        }

        ConfirmManager.request(player.getUniqueId(), () -> {
            reset(target);
            player.sendMessage("§aReset thành công!");
        });

        player.sendMessage("§cGõ /aoi confirm để xác nhận (10s)");
    }

    @Subcommand("confirm")
    public void onConfirm(Player player) {

        boolean ok = ConfirmManager.confirm(player.getUniqueId());

        if (!ok) {
            player.sendMessage("§cKhông có hành động hoặc đã hết hạn!");
            return;
        }

        player.sendMessage("§aXác nhận thành công!");
    }

    // ================= INTERNAL =================

    private void reset(OfflinePlayer target) {
        var c = playerManager.get(target.getUniqueId());
        if (c != null) {
            c.setRealm("Phàm nhân");
            c.setLevel(1);
            c.setExp(0);
        }
    }
}
