package vn.aoi.cultivation.config;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Objects;

public final class ConfigManager {

    private final JavaPlugin plugin;

    private File configFile;
    private File messagesFile;
    private File realmsFile;
    private File mobDropsFile;
    private File mobRewardsFile;
    private File shopsFile;

    private File menusFolder;
    private File mainMenuFile;
    private File shopMenuFile;

    private YamlConfiguration config;
    private YamlConfiguration messages;
    private YamlConfiguration realms;
    private YamlConfiguration mobDrops;
    private YamlConfiguration mobRewards;
    private YamlConfiguration shops;

    private ShopConfig shopConfig;

    private MenuConfig mainMenuConfig;
    private MenuConfig shopMenuConfig;

    public ConfigManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void load() {

        createDefaults();

        loadFiles();

        validate();

        loadConfigurations();
    }

    public void reload() {

        loadFiles();

        validate();

        loadConfigurations();
    }

    private void createDefaults() {

        plugin.saveDefaultConfig();

        saveIfMissing("messages.yml");
        saveIfMissing("realms.yml");
        saveIfMissing("mob_drops.yml");
        saveIfMissing("mob_rewards.yml");
        saveIfMissing("shops.yml");

        createMenuDefaults();
    }

    private void createMenuDefaults() {

        menusFolder =
                new File(
                        plugin.getDataFolder(),
                        "menus"
                );

        if (!menusFolder.exists()) {
            menusFolder.mkdirs();
        }

        saveMenuIfMissing("main_menu.yml");
        saveMenuIfMissing("shop_menu.yml");
    }

    private void saveMenuIfMissing(String fileName) {

        File file =
                new File(
                        menusFolder,
                        fileName
                );

        if (!file.exists()) {
            plugin.saveResource(
                    "menus/" + fileName,
                    false
            );
        }
    }

    private void saveIfMissing(String fileName) {

        File file =
                new File(
                        plugin.getDataFolder(),
                        fileName
                );

        if (!file.exists()) {
            plugin.saveResource(
                    fileName,
                    false
            );
        }
    }

    private void loadFiles() {

        configFile =
                new File(
                        plugin.getDataFolder(),
                        "config.yml"
                );

        messagesFile =
                new File(
                        plugin.getDataFolder(),
                        "messages.yml"
                );

        realmsFile =
                new File(
                        plugin.getDataFolder(),
                        "realms.yml"
                );

        mobDropsFile =
                new File(
                        plugin.getDataFolder(),
                        "mob_drops.yml"
                );

        mobRewardsFile =
                new File(
                        plugin.getDataFolder(),
                        "mob_rewards.yml"
                );

        shopsFile =
                new File(
                        plugin.getDataFolder(),
                        "shops.yml"
                );

        menusFolder =
                new File(
                        plugin.getDataFolder(),
                        "menus"
                );

        mainMenuFile =
                new File(
                        menusFolder,
                        "main_menu.yml"
                );

        shopMenuFile =
                new File(
                        menusFolder,
                        "shop_menu.yml"
                );

        config =
                YamlConfiguration.loadConfiguration(
                        configFile
                );

        messages =
                YamlConfiguration.loadConfiguration(
                        messagesFile
                );

        realms =
                YamlConfiguration.loadConfiguration(
                        realmsFile
                );

        mobDrops =
                YamlConfiguration.loadConfiguration(
                        mobDropsFile
                );

        mobRewards =
                YamlConfiguration.loadConfiguration(
                        mobRewardsFile
                );

        shops =
                YamlConfiguration.loadConfiguration(
                        shopsFile
                );
    }

    private void validate() {

        Objects.requireNonNull(
                config,
                "config.yml not loaded"
        );

        Objects.requireNonNull(
                messages,
                "messages.yml not loaded"
        );

        Objects.requireNonNull(
                realms,
                "realms.yml not loaded"
        );

        Objects.requireNonNull(
                mobDrops,
                "mob_drops.yml not loaded"
        );

        Objects.requireNonNull(
                mobRewards,
                "mob_rewards.yml not loaded"
        );

        Objects.requireNonNull(
                shops,
                "shops.yml not loaded"
        );

        if (!mainMenuFile.exists()) {
            throw new IllegalStateException(
                    "menus/main_menu.yml not found"
            );
        }

        if (!shopMenuFile.exists()) {
            throw new IllegalStateException(
                    "menus/shop_menu.yml not found"
            );
        }
    }

    private void loadConfigurations() {

        shopConfig =
                new ShopConfig(
                        shopsFile
                );

        mainMenuConfig =
                new MenuConfig(
                        mainMenuFile
                );

        shopMenuConfig =
                new MenuConfig(
                        shopMenuFile
                );
    }

    public YamlConfiguration getConfigYaml() {
        return config;
    }

    public YamlConfiguration getMessagesYaml() {
        return messages;
    }

    public YamlConfiguration getRealmsYaml() {
        return realms;
    }

    public YamlConfiguration getMobDropsYaml() {
        return mobDrops;
    }

    public YamlConfiguration getMobRewardsYaml() {
        return mobRewards;
    }

    public YamlConfiguration getShopsYaml() {
        return shops;
    }

    public ShopConfig getShopConfig() {
        return shopConfig;
    }

    public MenuConfig getMainMenuConfig() {
        return mainMenuConfig;
    }

    public MenuConfig getShopMenuConfig() {
        return shopMenuConfig;
    }
} 
