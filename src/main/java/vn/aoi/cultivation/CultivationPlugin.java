package vn.aoi.cultivation;

import vn.aoi.cultivation.core.security.AntiDupeManager;
import vn.aoi.cultivation.core.security.ClickCooldownManager;
import vn.aoi.cultivation.core.security.TransactionGuard;
import vn.aoi.cultivation.listener.InventoryClickListener;
import vn.aoi.cultivation.listener.InventoryCloseListener;
import vn.aoi.cultivation.listener.PlayerJoinListener;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class CultivationPlugin extends JavaPlugin {

    private static CultivationPlugin instance;

    private ClickCooldownManager clickCooldownManager;
    private AntiDupeManager antiDupeManager;
    private TransactionGuard transactionGuard;

    @Override
    public void onEnable() {

        instance = this;

        saveDefaultConfig();

        initializeManagers();
        registerListeners();

        getLogger().info("======================================");
        getLogger().info(" CultivationPlugin enabled");
        getLogger().info(" Security Layer: ACTIVE");
        getLogger().info(" AntiDupe Layer: ACTIVE");
        getLogger().info(" Transaction Layer: ACTIVE");
        getLogger().info("======================================");
    }

    @Override
    public void onDisable() {

        if (clickCooldownManager != null) {
            clickCooldownManager.clearAll();
        }

        if (antiDupeManager != null) {
            antiDupeManager.clearAll();
        }

        if (transactionGuard != null) {
            transactionGuard.clearAll();
        }

        Bukkit.getScheduler().cancelTasks(this);

        HandlerList.unregisterAll(this);

        instance = null;

        getLogger().info("CultivationPlugin disabled safely.");
    }

    private void initializeManagers() {

        this.clickCooldownManager = new ClickCooldownManager();
        this.antiDupeManager = new AntiDupeManager();
        this.transactionGuard = new TransactionGuard(this);
    }

    private void registerListeners() {

        PluginManager pluginManager = Bukkit.getPluginManager();

        pluginManager.registerEvents(
                new InventoryClickListener(
                        clickCooldownManager,
                        transactionGuard
                ),
                this
        );

        pluginManager.registerEvents(
                new InventoryCloseListener(
                        antiDupeManager
                ),
                this
        );

        pluginManager.registerEvents(
                new PlayerJoinListener(
                        this,
                        clickCooldownManager,
                        antiDupeManager,
                        transactionGuard
                ),
                this
        );
    }

    public static CultivationPlugin getInstance() {
        return instance;
    }

    public ClickCooldownManager getClickCooldownManager() {
        return clickCooldownManager;
    }

    public AntiDupeManager getAntiDupeManager() {
        return antiDupeManager;
    }

    public TransactionGuard getTransactionGuard() {
        return transactionGuard;
    }

    @Override
    public boolean onCommand(CommandSender sender,
                             Command command,
                             String label,
                             String[] args) {

        if (!command.getName().equalsIgnoreCase("cultivation")) {
            return false;
        }

        sender.sendMessage("§aCultivationPlugin đang hoạt động.");
        return true;
    }
}
