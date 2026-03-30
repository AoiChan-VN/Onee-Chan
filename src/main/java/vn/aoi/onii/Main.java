package vn.aoi.onii;

import org.bukkit.plugin.java.JavaPlugin;
import vn.aoi.onii.command.TutienCommand;
import vn.aoi.onii.player.PlayerManager;
import vn.aoi.onii.cultivation.CultivationService;

public class Main extends JavaPlugin {

    private static Main instance;
    private PlayerManager playerManager;
    private CultivationService cultivationService;

    public static Main get() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();

        playerManager = new PlayerManager();
        cultivationService = new CultivationService(playerManager);

        getCommand("tutien").setExecutor(new TutienCommand(playerManager, cultivationService));

        getLogger().info("OniiTuTien Enabled!");
    }

    public PlayerManager getPlayerManager() { return playerManager; }
    public CultivationService getCultivationService() { return cultivationService; }
}
