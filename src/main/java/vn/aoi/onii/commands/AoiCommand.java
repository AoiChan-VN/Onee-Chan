package vn.aoi.onii.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import vn.aoi.onii.player.*;
import vn.aoi.onii.realm.RealmProgression;

public class AoiCommand implements CommandExecutor {

    private final PlayerManager manager;

    public AoiCommand(PlayerManager manager) {
        this.manager = manager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player player)) return true;

        PlayerData data = manager.get(player.getUniqueId(), player.getName());

        // INFO | Thông tin chi tiết
        if (args.length == 1 && args[0].equalsIgnoreCase("info")) {

            sender.sendMessage("〖❀〗Thông Tin〖❀〗");
            sender.sendMessage("§6Đạo hữu: §f" + data.getName());
            sender.sendMessage("§6Cảnh giới: §f" + data.getRealm().getDisplay());
            sender.sendMessage("§6Tu vi: §f" + data.getStage().getDisplay());
            sender.sendMessage("§6EXP: §f" + data.getExp());

            return true;
        }

        // TOP | Bảng xếp hạng
        if (args.length == 1 && args[0].equalsIgnoreCase("top")) {
            
            player.openInventory(new TopGUI(Main.getInstance().getDatabase()).create());
           
            return true;
        }
        
        // BREAKTHROUGH | ⇮ Kinh nghiệm
        if (args.length == 1 && args[0].equalsIgnoreCase("break")) {

            int required = RealmProgression.getRequiredExp(data.getRealm(), data.getStage());

            if (data.getExp() < required) {
                player.sendMessage("§cChưa đủ tu vi để đột phá!");
                return true;
            }

            // STAGE | ⇮ Tu vi
            var nextStage = RealmProgression.nextStage(data.getStage());

            if (nextStage != null) {
                data.setStage(nextStage);
                data.setExp(0);
                manager.save(data);

                player.sendMessage("§aĐột phá thành công → " + nextStage.getDisplay());
                return true;
            }

            // REALM | ⇮ Cảnh giới
            var nextRealm = RealmProgression.nextRealm(data.getRealm());

            if (nextRealm != null) {
                data.setRealm(nextRealm);
                data.setStage(vn.aoi.onii.enums.Stage.SO_KY);
                data.setExp(0);
                manager.save(data);

                player.sendMessage("§6Đại cảnh giới đột phá → " + nextRealm.getDisplay());
                return true;
            }

            player.sendMessage("§cĐã đạt cảnh giới tối cao!");
            return true;
        }

        // TEST ADD EXP
        if (args.length == 2 && args[0].equalsIgnoreCase("addexp")) {
            int amount = Integer.parseInt(args[1]);
            data.setExp(data.getExp() + amount);
            manager.save(data);

            player.sendMessage("§a+EXP: " + amount);
            return true;
        }

        player.sendMessage("/aoi break | /aoi addexp <số>");
        return true;
    }
}
