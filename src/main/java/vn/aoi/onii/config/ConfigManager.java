package vn.aoi.onii.config;

import org.bukkit.configuration.file.*;
import vn.aoi.onii.Main;
import java.io.File;

public class ConfigManager {

    public static FileConfiguration realms, mobs, messages;

    public static void init(Main p) {
        realms = load(p,"realms.yml");
        mobs = load(p,"mobs.yml");
        messages = load(p,"messages.yml");
    }

    private static FileConfiguration load(Main p,String name){
        File f = new File(p.getDataFolder(),name);
        if(!f.exists()) p.saveResource(name,false);
        return YamlConfiguration.loadConfiguration(f);
    }
}
