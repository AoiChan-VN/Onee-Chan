package vn.aoi.onii.commands.context;

import co.aikar.commands.InvalidCommandArgument;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.UUID;

public class ACFContext {

    public static void register(co.aikar.commands.PaperCommandManager manager) {

        manager.getCommandContexts().registerContext(OfflinePlayer.class, c -> {
            String input = c.popFirstArg();

            OfflinePlayer player = Bukkit.getOfflinePlayer(input);

            if (player == null || player.getUniqueId() == null) {
                throw new InvalidCommandArgument("Player không tồn tại!");
            }

            return player;
        });

        manager.getCommandContexts().registerContext(UUID.class, c -> {
            try {
                return UUID.fromString(c.popFirstArg());
            } catch (Exception e) {
                throw new InvalidCommandArgument("UUID không hợp lệ!");
            }
        });
    }
}
