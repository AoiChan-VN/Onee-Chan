package aoichan.crystal.core;

import aoichan.crystal.AoiMain;

public class ReloadManager {

    public static void reload(AoiMain plugin) {
        plugin.reloadConfig();
        if (plugin.getGemsManager() != null) plugin.getGemsManager().reload();
        plugin.getLogger().info("GemsUltimate reloaded.");
    }
}
