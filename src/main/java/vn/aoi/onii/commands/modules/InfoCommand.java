package vn.aoi.onii.commands.modules;

import cloud.commandframework.annotations.CommandMethod;
import cloud.commandframework.annotations.CommandDescription;
import org.bukkit.entity.Player;
import vn.aoi.onii.Main;
import vn.aoi.onii.commands.framework.AbstractCommand;
import vn.aoi.onii.commands.framework.annotations.CommandCooldown;
import vn.aoi.onii.config.MessageManager;
import vn.aoi.onii.player.PlayerData;

public class InfoCommand extends AbstractCommand {

    public InfoCommand(Main plugin) {
        super(plugin);
    }

    @Override
    public void register(cloud.commandframework.annotations.AnnotationParser<?> parser) {
        parser.parse(this);
    }

    @CommandMethod("aoi info")
    @CommandDescription("Xem thông tin tu vi")
    @CommandCooldown(seconds = 3)
    public void info(Player player) {

        PlayerData data = plugin.getPlayerManager().get(player);
        if (data == null) {
            player.sendMessage("§cKhông tìm thấy dữ liệu!");
            return;
        }

        player.sendMessage(MessageManager.get("info.header"));
        player.sendMessage(MessageManager.get("info.realm")
                .replace("{realm}", data.getRealm()));
        player.sendMessage(MessageManager.get("info.level")
                .replace("{level}", String.valueOf(data.getLevel())));
        player.sendMessage(MessageManager.get("info.exp")
                .replace("{exp}", String.valueOf(data.getExp())));
    }
}
