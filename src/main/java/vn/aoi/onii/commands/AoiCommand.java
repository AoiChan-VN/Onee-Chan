package vn.aoi.onii.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import vn.aoi.onii.database.Database;
import vn.aoi.onii.gui.ProfileGUI;
import vn.aoi.onii.leaderboard.TopGUI;
import vn.aoi.onii.player.*;
import vn.aoi.onii.realm.RealmProgression;
import vn.aoi.onii.shop.ShopManager;

public class AoiCommand implements CommandExecutor {

    private final PlayerManager manager;
    private final Database db;

    public AoiCommand(PlayerManager manager, Database db) {
        this.manager = manager;
        this.db = db;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player player)) return true;

        PlayerData data = manager.get(player.getUniqueId(), player.getName());

        // PROFILE
        if (args.length == 1 && args[0].equalsIgnoreCase("profile")) {
            player.openInventory(ProfileGUI.create(data));
            return true;
        }

        // SET SECT (ADMIN)
        if (args.length == 2 && args[0].equalsIgnoreCase("setsect")) {
            data.setSect(args[1]);
            manager.save(data);
            player.sendMessage("§aĐã gia nhập tông môn: " + args[1]);
            return true;
        }

        // SHOP
        if (args.length == 1 && args[0].equalsIgnoreCase("shop")) {
            player.openInventory(new ShopManager().createShop(1));
            return true;
        }

        // TOP
        if (args.length == 1 && args[0].equalsIgnoreCase("top")) {
            player.openInventory(new TopGUI(db).create());
            return true;
        }

        // BREAK
        if (args.length == 1 && args[0].equalsIgnoreCase("break")) {

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
                    data.setStage(vn.aoi.onii.enums.Stage.SO_KY);
                }
            }

            data.setExp(0);
            manager.save(data);

            player.sendMessage("§aĐột phá thành công!");
            return true;
        }

        player.sendMessage("/aoi profile | shop | top | break");
        return true;
    }
}
