package aoichan.crystal.bootstrap;

import aoichan.crystal.core.engine.CrystalEngine;
import org.bukkit.plugin.java.JavaPlugin;

public final class AoiMain extends JavaMain {

    private static AoiMain instance;
    private CrystalEngine engine;

    @Override
    public void onEnable() {

        instance = this;

        // 【!】Code: Khởi động engine chính
        engine = new CrystalEngine(this);
        engine.start();

        getLogger().info("Crystal-MMORPG Khởi động");
    }

    @Override
    public void onDisable() {

        // 【!】Code: shutdown engine
        if (engine != null) {
            engine.shutdown();

        getlogger().info("Crystal-MMORPG Đã tắt");
        }

    }

    // 【!】Code: lấy instance plugin
    public static AoiMain get() {
        return instance;
    }

}
