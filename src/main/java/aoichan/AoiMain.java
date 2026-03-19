// AoiCore - (Single Server Edition)
// Author: AoiChan
// Note: Clean, production-ready, async-safe

package aoichan;

import aoichan.bootstrap.Loader;
import aoichan.bootstrap.Shutdown;
import org.bukkit.plugin.java.JavaPlugin;

public class AoiMain extends JavaPlugin {

    private static AoiMain instance;

    @Override
    public void onEnable() {
        instance = this;
        Loader.init(this);
    }

    @Override
    public void onDisable() {
        Shutdown.execute();
    }

    public static AoiMain get() {
        return instance;
    }
}
