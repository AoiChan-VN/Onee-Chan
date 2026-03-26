package vn.aoi.onii;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import vn.aoi.onii.commands.AoiCommand;
import vn.aoi.onii.database.Database;
import vn.aoi.onii.listeners.ChatListener;
import vn.aoi.onii.player.PlayerManager;
import vn.aoi.onii.shop.ShopListener;

public class Main extends JavaPlugin {

    private static Main instance;
    private Economy econ;
    private Database database;
    private PlayerManager manager;

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();
        saveResource("shop.yml", false);

        setupEconomy();

        database = new Database(this);
        database.connect();
        database.createTable();

        manager = new PlayerManager(database);

        getCommand("aoi").setExecutor(new AoiCommand(manager));

        getServer().getPluginManager().registerEvents(new ChatListener(manager), this);
        getServer().getPluginManager().registerEvents(new ShopListener(manager, econ), this);
    }

    public static Main getInstance() {
        return instance;
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) return false;

        RegisteredServiceProvider<Economy> rsp =
                getServer().getServicesManager().getRegistration(Economy.class);

        if (rsp == null) return false;

        econ = rsp.getProvider();
        return econ != null;
    }
}
