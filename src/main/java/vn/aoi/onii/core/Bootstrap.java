package vn.aoi.onii.core;

import org.bukkit.Bukkit;
import vn.aoi.onii.Main;
import vn.aoi.onii.classsystem.*;
import vn.aoi.onii.classsystem.impl.*;
import vn.aoi.onii.command.*;
import vn.aoi.onii.config.ConfigManager;
import vn.aoi.onii.database.DatabaseManager;
import vn.aoi.onii.mana.ManaRegenTask;
import vn.aoi.onii.player.PlayerManager;
import vn.aoi.onii.scheduler.AsyncExecutor;
import vn.aoi.onii.skill.*;
import vn.aoi.onii.skill.config.SkillConfigManager;
import vn.aoi.onii.skill.impl.*;
import vn.aoi.onii.skill.listener.SkillListener;

import java.io.File;

public class Bootstrap {

    private final Main plugin;

    private ConfigManager config;
    private AsyncExecutor executor;
    private DatabaseManager database;
    private PlayerManager playerManager;

    public Bootstrap(Main plugin) {
        this.plugin = plugin;
    }

    public void init() {
        config = new ConfigManager(plugin);
        config.loadAll();

        executor = new AsyncExecutor();

        database = new DatabaseManager(plugin);
        database.init();

        playerManager = new PlayerManager(plugin, database, executor);
        playerManager.init();

        ClassRegistry classRegistry = new ClassRegistry();
        classRegistry.register(new WarriorClass());
        classRegistry.register(new MageClass());
        ClassManager classManager = new ClassManager(playerManager, classRegistry);

        SkillConfigManager skillConfig = new SkillConfigManager();
        skillConfig.load(new File(plugin.getDataFolder(), "skills.json"));

        SkillRegistry skillRegistry = new SkillRegistry();
        skillRegistry.register(new WarriorSlash());
        skillRegistry.register(new MageFireball());

        CooldownManager cooldown = new CooldownManager();

        SkillManager skillManager = new SkillManager(
                skillRegistry,
                cooldown,
                classManager,
                playerManager,
                skillConfig
        );

        Bukkit.getPluginManager().registerEvents(new SkillListener(skillManager, classManager), plugin);

        new ManaRegenTask(plugin, playerManager);

        CommandManager commandManager = new CommandManager(plugin, playerManager, classManager);
        commandManager.register();
    }

    public void shutdown() {
        playerManager.shutdown();
        database.shutdown();
        executor.shutdown();
    }
}
