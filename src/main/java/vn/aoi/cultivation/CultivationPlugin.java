package vn.aoi.cultivation;

import vn.aoi.cultivation.config.ConfigManager;
import vn.aoi.cultivation.core.security.AntiDupeManager;
import vn.aoi.cultivation.core.security.ClickCooldownManager;
import vn.aoi.cultivation.core.security.TransactionGuard;
import vn.aoi.cultivation.gui.menu.ShopMenu;
import vn.aoi.cultivation.listener.InventoryClickListener;
import vn.aoi.cultivation.listener.InventoryCloseListener;
import vn.aoi.cultivation.listener.PlayerJoinListener;
import vn.aoi.cultivation.service.ShopService;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class CultivationPlugin extends JavaPlugin {

    private static CultivationPlugin instance;

    private ConfigManager configManager;

    private ClickCooldownManager clickCooldownManager;
    private AntiDupeManager antiDupeManager;
    private TransactionGuard transactionGuard;

    private ShopService shopService;
    private ShopMenu shopMenu;

    @Override
    public void onEnable() {

        instance = this;

        initializeConfiguration();

        initializeManagers();

        initializeServices();

        registerListeners();

        getLogger().info("=================================");
        getLogger().info("CultivationPlugin Enabled");
        getLogger().info("Configuration Loaded");
        getLogger().info("Security Layer Loaded");
        getLogger().info("Shop Layer Loaded");
        getLogger().info("=================================");
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

        getLogger().info("CultivationPlugin Disabled");
    }

    private void initializeConfiguration() {

        configManager =
                new ConfigManager(this);

        configManager.load();
    }

    private void initializeManagers() {

        clickCooldownManager =
                new ClickCooldownManager();

        antiDupeManager =
                new AntiDupeManager();

        transactionGuard =
                new TransactionGuard(this);
    }

    private void initializeServices() {

        shopService =
                new ShopService(
                        configManager.getShopConfig()
                );

        shopMenu =
                new ShopMenu(
                        antiDupeManager
                );
    }

    private void registerListeners() {

        PluginManager pluginManager =
                Bukkit.getPluginManager();

        pluginManager.registerEvents(
                new InventoryClickListener(
                        clickCooldownManager,
                        transactionGuard,
                        shopMenu
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

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public ShopService getShopService() {
        return shopService;
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
