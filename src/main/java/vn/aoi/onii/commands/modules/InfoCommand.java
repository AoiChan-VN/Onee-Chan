@CommandMethod("aoi info")
@CommandCooldown(seconds = 3)
public void info(Player player) {

    if (!checkCooldown(player, "info", 3)) return;

    var data = plugin.getPlayerManager().get(player);

    player.sendMessage(MessageManager.get("info.header"));
    player.sendMessage(MessageManager.get("info.realm")
            .replace("{realm}", data.getRealm()));
    player.sendMessage(MessageManager.get("info.level")
            .replace("{level}", String.valueOf(data.getLevel())));
    player.sendMessage(MessageManager.get("info.exp")
            .replace("{exp}", String.valueOf(data.getExp())));
} 
