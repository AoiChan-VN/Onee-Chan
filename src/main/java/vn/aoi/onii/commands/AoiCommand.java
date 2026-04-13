package vn.aoi.onii.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import vn.aoi.onii.commands.confirm.ConfirmManager;
import vn.aoi.onii.commands.cooldown.CommandCooldown;
import vn.aoi.onii.manager.PlayerManager;
import vn.aoi.onii.manager.RealmManager;
import vn.aoi.onii.model.Cultivator;

@CommandAlias("aoi")
public class AoiCommand extends BaseCommand {

    private final PlayerManager playerManager;
    private final RealmManager realmManager;

    public AoiCommand(PlayerManager playerManager, RealmManager realmManager) {
        this.playerManager = playerManager;
        this.realmManager = realmManager;
    }

    @Subcommand("info")
    public void onInfo(Player player) {
        Cultivator c = playerManager.get(player.getUniqueId());

        if (c == null) {
            player.sendMessage("§cDữ liệu chưa được tải!");
            return;
        }

        player.sendMessage("§6=== THÔNG TIN TU VI ===");
        player.sendMessage("§eCảnh giới: §f" + c.getRealm());
        player.sendMessage("§eCấp độ: §f" + c.getLevel());
        player.sendMessage("§eKinh nghiệm: §f" + c.getExp());
    }

    @Subcommand("addexp")
    @CommandPermission("aoi.admin")
    @CommandCompletion("@players")
    public void onAddExp(CommandSender sender, Player target, double amount) {
        if (sender instanceof Player player) {
            if (CommandCooldown.isOnCooldown("addexp", player.getUniqueId(), 3000)) {
                player.sendMessage("§cThao tác quá nhanh, vui lòng đợi!");
                return;
            }
        }

        Cultivator c = playerManager.get(target.getUniqueId());
        if (c != null) {
            c.setExp(c.getExp() + amount);
            sender.sendMessage("§aĐã thêm " + amount + " EXP cho " + target.getName());
        } else {
            sender.sendMessage("§cNgười chơi không tồn tại hoặc chưa load dữ liệu!");
        }
    }

    @Subcommand("setrealm")
    @CommandPermission("aoi.admin")
    public void onSetRealm(CommandSender sender, OfflinePlayer target, String realm) {
        
        if (!realmManager.exists(realm)) {
            sender.sendMessage("§cCảnh giới này không tồn tại!");
            return;
        }

        Cultivator c = playerManager.get(target.getUniqueId());
        if (c != null) {
            c.setRealm(realm);
            c.setLevel(1);
            c.setExp(0);
            sender.sendMessage("§aĐã đặt cảnh giới của " + target.getName() + " thành: " + realm);
        } else {
            sender.sendMessage("§cKhông tìm thấy dữ liệu người chơi!");
        }
    }

    @Subcommand("reset")
    @CommandPermission("aoi.admin")
    public void onReset(CommandSender sender, OfflinePlayer target) {
        if (!(sender instanceof Player player)) {
            resetLogic(target);
            sender.sendMessage("§aĐã reset tu vi cho " + target.getName());
            return;
        }

        ConfirmManager.request(player.getUniqueId(), () -> {
            resetLogic(target);
            player.sendMessage("§aĐã hoàn tất reset tu vi!");
        });
        player.sendMessage("§eXác nhận hành động bằng cách gõ: §f/aoi confirm");
    }

    @Subcommand("confirm")
    public void onConfirm(Player player) {
        if (!ConfirmManager.confirm(player.getUniqueId())) {
            player.sendMessage("§cYêu cầu xác nhận đã hết hạn hoặc không tồn tại!");
        }
    }

    // Đổi tên để tránh trùng lặp với logic command nếu cần
    private void resetLogic(OfflinePlayer target) {
        Cultivator c = playerManager.get(target.getUniqueId());
        if (c != null) {
            c.setRealm("Phàm nhân");
            c.setLevel(1);
            c.setExp(0);
        }
    }
}
