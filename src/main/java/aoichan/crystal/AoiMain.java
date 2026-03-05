package aoichan.crystal;

import aoichan.crystal.core.autosave.AutoSaveManager;
import aoichan.crystal.core.cache.DirtyTracker;
import aoichan.crystal.core.cache.GemCache;
import aoichan.crystal.core.language.LangManager;
import aoichan.crystal.core.manager.GemsManager;
import aoichan.crystal.storage.StorageManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

// [!] Code: Main Plugin Class - Crystal Ultimate Production
public class AoiMain extends JavaPlugin {

    private static AoiMain instance;

    private GemCache gemCache;
    private DirtyTracker dirtyTracker;
    private StorageManager storageManager;
    private GemsManager gemsManager;
    private AutoSaveManager autoSaveManager;
    private LangManager langManager;

    public static AoiMain getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();

        // [!] Core Systems Init
        this.gemCache = new GemCache();
        this.dirtyTracker = new DirtyTracker();
        this.storageManager = new StorageManager(this);
        this.langManager = new LangManager(this);
        this.gemsManager = new GemsManager(this, gemCache, dirtyTracker, storageManager);
        this.autoSaveManager = new AutoSaveManager(this, dirtyTracker, storageManager);

        storageManager.initialize();
        langManager.loadLanguage();

        Bukkit.getConsoleSender().sendMessage("§a[Crystal Ultimate] Plugin Enabled.");
    }

    @Override
    public void onDisable() {

        // [!] Safe Shutdown Save
        autoSaveManager.shutdownSaveSync();
        storageManager.shutdown();

        Bukkit.getConsoleSender().sendMessage("§c[Crystal Ultimate] Plugin Disabled Safely.");
    }

    public GemCache getGemCache() {
        return gemCache;
    }

    public DirtyTracker getDirtyTracker() {
        return dirtyTracker;
    }

    public StorageManager getStorageManager() {
        return storageManager;
    }

    public GemsManager getGemsManager() {
        return gemsManager;
    }

    public AutoSaveManager getAutoSaveManager() {
        return autoSaveManager;
    }

    public LangManager getLangManager() {
        return langManager;
    }
}
