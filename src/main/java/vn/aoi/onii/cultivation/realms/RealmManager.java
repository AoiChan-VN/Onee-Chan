package vn.aoi.onii.cultivation;

import org.bukkit.configuration.ConfigurationSection;
import vn.aoi.onii.Main;

import java.util.HashMap;
import java.util.Map;

public class RealmManager {

    private final Main plugin;
    private final Map<String, Realm> realms = new HashMap<>();

    public RealmManager(Main plugin) {
        this.plugin = plugin;
    }

    public void loadRealms() {
        ConfigurationSection section = plugin.getConfig().getConfigurationSection("canhgioi");

        for (String key : section.getKeys(false)) {

            ConfigurationSection r = section.getConfigurationSection(key);

            int max = r.getInt("max-level");
            String next = r.getString("next-rank");
            boolean thienKiep = r.getBoolean("is-thien-kiep", false);

            Map<Integer, Integer> expMap = new HashMap<>();

            if (r.contains("levels")) {
                for (String lvl : r.getConfigurationSection("levels").getKeys(false)) {
                    expMap.put(Integer.parseInt(lvl),
                            r.getConfigurationSection("levels." + lvl).getInt("exp"));
                }
            } else {
                expMap.put(1, r.getInt("exp-required"));
            }

            realms.put(key, new Realm(key, max, expMap, next, thienKiep));
        }
    }

    public Realm getRealm(String name) {
        return realms.get(name);
    }
} 
