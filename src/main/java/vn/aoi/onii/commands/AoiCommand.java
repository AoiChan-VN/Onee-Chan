package vn.aoi.onii.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import vn.aoi.onii.Main;
import vn.aoi.onii.enums.*;
import vn.aoi.onii.gui.ProfileGUI;
import vn.aoi.onii.leaderboard.TopGUI;
import vn.aoi.onii.player.*;
import vn.aoi.onii.realm.RealmProgression;
import vn.aoi.onii.shop.ShopManager;

public class AoiCommand implements CommandExecutor {

    private final PlayerManager manager;

    public AoiCommand(PlayerManager manager) {
        this.manager = manager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        // ===== HELP =====
        if (args.length == 0) {
            sender.sendMessage("§6§l=== AOI SYSTEM ===");
            sender.sendMessage("§e/aoi profile §7→ Thông tin");
            sender.sendMessage("§e/aoi shop §7→ Cửa hàng");
            sender.sendMessage("§e/aoi top §7→ BXH");
            sender.sendMessage("§e/aoi break §7→ Đột phá");
            sender.sendMessage("§e/aoi info [player]");
            
            if (sender.hasPermission("aoi.admin")) {
                sender.sendMessage("§c§l--- ADMIN ---");
                sender.sendMessage("§e/aoi setexp <p> <amount>");
                sender.sendMessage("§e/aoi addexp <p> <amount>");
                sender.sendMessage("§e/aoi setrealm <p> <realm>");
                sender.sendMessage("§e/aoi setstage <p> <stage>");
                sender.sendMessage("§e/aoi settech <p> <tier>");
                sender.sendMessage("§e/aoi reload");
            }
            return true;
        }

        // ===== PLAYER ONLY =====
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Player only!");
            return true;
        }

        PlayerData data = manager.get(player.getUniqueId(), player.getName());

        switch (args[0].toLowerCase()) {

            case "profile":
                player.openInventory(ProfileGUI.create(data));
                break;

            case "shop":
                player.openInventory(new ShopManager().createShop(1));
                break;

            case "top":
                player.openInventory(new TopGUI(Main.getInstance().getDatabase()).create());
                break;

            case "info": {
                Player target = (args.length >= 2) ? Bukkit.getPlayer(args[1]) : player;

                if (target == null) {
                    player.sendMessage("§cKhông tìm thấy người chơi!");
                    return true;
                }

                PlayerData d = manager.get(target.getUniqueId(), target.getName());

                player.sendMessage("§6=== Thông Tin ===");
                player.sendMessage("§eĐạo hữu: §f" + d.getName());
                player.sendMessage("§eCảnh giới: §f" + d.getRealm().getDisplay());
                player.sendMessage("§eTu vi: §f" + d.getStage().getDisplay());
                player.sendMessage("§eEXP: §f" + d.getExp());
                player.sendMessage("§eTông môn: §f" + d.getSect());
                player.sendMessage("§eCông pháp: §f" + d.getTechnique());
                break;
            }

            case "break": {
                int req = RealmProgression.getRequiredExp(data.getRealm(), data.getStage());

                if (data.getExp() < req) {
                    player.sendMessage("§cChưa đủ EXP!");
                    return true;
                }

                var nextStage = RealmProgression.nextStage(data.getStage());

                if (nextStage != null) {
                    data.setStage(nextStage);
                } else {
                    var nextRealm = RealmProgression.nextRealm(data.getRealm());
                    if (nextRealm != null) {
                        data.setRealm(nextRealm);
                        data.setStage(Stage.SO_KY);
                    }
                }

                data.setExp(0);
                manager.save(data);

                player.sendMessage("§aĐột phá thành công!");
                break;
            }

            default:
                handleAdmin(player, args);
                break;
        }

        return true;
    }

    private void handleAdmin(Player player, String[] args) {

        if (!player.hasPermission("aoi.admin")) {
            player.sendMessage("§cKhông có quyền!");
            return;
        }

        try {

            switch (args[0].toLowerCase()) {

                case "setexp": {
                    Player t = Bukkit.getPlayer(args[1]);
                    int val = Integer.parseInt(args[2]);

                    var d = manager.get(t.getUniqueId(), t.getName());
                    d.setExp(val);
                    manager.save(d);

                    player.sendMessage("§aSet EXP!");
                    break;
                }

                case "addexp": {
                    Player t = Bukkit.getPlayer(args[1]);
                    int val = Integer.parseInt(args[2]);

                    var d = manager.get(t.getUniqueId(), t.getName());
                    d.setExp(d.getExp() + val);
                    manager.save(d);

                    player.sendMessage("§aAdd EXP!");
                    break;
                }

                case "setrealm": {
                    Player t = Bukkit.getPlayer(args[1]);
                    Realm r = Realm.valueOf(args[2].toUpperCase());

                    var d = manager.get(t.getUniqueId(), t.getName());
                    d.setRealm(r);
                    manager.save(d);

                    player.sendMessage("§aSet realm!");
                    break;
                }

                case "setstage": {
                    Player t = Bukkit.getPlayer(args[1]);
                    Stage s = Stage.valueOf(args[2].toUpperCase());

                    var d = manager.get(t.getUniqueId(), t.getName());
                    d.setStage(s);
                    manager.save(d);

                    player.sendMessage("§aSet stage!");
                    break;
                }

                case "settech": {
                    Player t = Bukkit.getPlayer(args[1]);

                    var d = manager.get(t.getUniqueId(), t.getName());
                    d.setTechnique(args[2].toUpperCase());
                    manager.save(d);

                    player.sendMessage("§aSet tech!");
                    break;
                }

                case "reload":
                    Main.getInstance().reloadConfig();
                    player.sendMessage("§aReload xong!");
                    break;

                default:
                    player.sendMessage("§cSai lệnh!");
            }

        } catch (Exception e) {
            player.sendMessage("§cSai cú pháp!");
        }
    }
}
