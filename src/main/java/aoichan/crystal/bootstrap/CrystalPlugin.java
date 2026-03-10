package aoichan.crystal.bootstrap;

import aoichan.crystal.platform.command.CrystalCommand;

import aoichan.crystal.core.gem.GemParser;

import aoichan.crystal.gameplay.gem.GemRegistry;
import aoichan.crystal.gameplay.drop.GemDropManager;

import aoichan.crystal.platform.listener.CombatListener;
import aoichan.crystal.platform.listener.EffectTriggerListener;
import aoichan.crystal.platform.listener.EquipmentChangeListener;
import aoichan.crystal.platform.listener.PlayerJoinListener;
import aoichan.crystal.platform.listener.MobDeathListener;

import org.bukkit.plugin.java.JavaPlugin;

public final class CrystalPlugin extends JavaPlugin {

    // [!] Code: Plugin instance
    private static CrystalPlugin instance;

    public static CrystalPlugin get() {
        return instance;
    }

    @Override
    public void onEnable() {

        instance = this;

        // [!] Code: Load configs
        saveDefaultConfig();

        // [!] Code: Load gem registry
        GemRegistry.load();

        // [!] Code: Register command
        getCommand("crystal").setExecutor(new CrystalCommand());

        // [!] Code: Register listeners
        registerListeners();

        // [!] Code: Drop gem
        GemDropManager.load();

        // [!] Code: Gem load/reload
        GemParser.load(getConfig());
        
        getLogger().info("Crystal Ultimate enabled.");
    }

    @Override
    public void onDisable() {

        getLogger().info("Crystal Ultimate disabled.");
    }

    // [!] Code: Listener registration
    private void registerListeners() {

        // [!] Code: Player join listener
        getServer().getPluginManager()
                .registerEvents(new PlayerJoinListener(), this);

        // [!] Code: Equipmentchange listener
        getServer().getPluginManager()
                .registerEvents(new EquipmentChangeListener(), this);

        // [!] Code: Combat listener
        getServer().getPluginManager()
                .registerEvents(new CombatListener(), this);

        // [!] Code: Mob death listener
        getServer().getPluginManager()
                .registerEvents(new MobDeathListener(), this);

        // [!] Code: Effect trigger listener
        getServer().getPluginManager()
                .registerEvents(new EffectTriggerListener(), this);
    }

}
