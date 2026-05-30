package vn.aoi.cultivation;

import org.bukkit.plugin.java.JavaPlugin;
import vn.aoi.cultivation.bootstrap.PluginBootstrap;
import vn.aoi.cultivation.data.cache.CultivationCache;
import vn.aoi.cultivation.data.repository.PlayerCultivationRepository;
import vn.aoi.cultivation.task.AsyncDataSaveTask;
import vn.aoi.cultivation.util.MiniMessageUtil;

import java.util.Objects;

public final class CultivationPlugin extends JavaPlugin {

    private static CultivationPlugin instance;

    private CultivationCache cache;
    private PlayerCultivationRepository repository;
    private AsyncDataSaveTask saveTask;

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        Objects.requireNonNull(getServer(), "Server instance is null during enable");

        this.saveDefaultConfig();

        // Bootstrap core systems
        PluginBootstrap bootstrap = new PluginBootstrap(this);
        bootstrap.initialize();

        this.cache = bootstrap.getCache();
        this.repository = bootstrap.getRepository();

        // Start async persistence loop
        this.saveTask = new AsyncDataSaveTask(cache, repository, this);
        this.saveTask.start();

        getComponentLogger().info(
                MiniMessageUtil.deserialize("<green>Cultivation Plugin enabled successfully.</green>")
        );
    }

    @Override
    public void onDisable() {
        if (saveTask != null) {
            saveTask.shutdown();
        }

        if (cache != null && repository != null) {
            cache.flushAllToRepository(repository);
        }

        getComponentLogger().info(
                MiniMessageUtil.deserialize("<yellow>Cultivation Plugin safely shut down.</yellow>")
        );
    }

    public static CultivationPlugin getInstance() {
        return instance;
    }
} 
