package vn.aoi.onii.manager;

import vn.aoi.onii.config.ConfigManager;
import vn.aoi.onii.manager.model.LevelInfo;
import vn.aoi.onii.manager.model.Realm;

import java.util.HashMap;
import java.util.Map;

public class RealmManager {

    private static final Map<String, Realm> realms = new HashMap<>();

    public static void load() {
        realms.clear();

        var section = ConfigManager.realms.getConfigurationSection("realms");
        if (section == null) return;

        for (String key : section.getKeys(false)) {

            int max = section.getInt(key + ".max-level");
            String next = section.getString(key + ".next-rank");
            boolean trib = section.getBoolean(key + ".is-thien-kiep");

            Map<Integer, LevelInfo> levels = new HashMap<>();

            if (section.isConfigurationSection(key + ".levels")) {
                for (String lvl : section.getConfigurationSection(key + ".levels").getKeys(false)) {
                    int l = Integer.parseInt(lvl);
                    int exp = section.getInt(key + ".levels." + lvl + ".exp");
                    String phase = section.getString(key + ".levels." + lvl + ".phase");

                    levels.put(l, new LevelInfo(exp, phase));
                }
            }

            realms.put(key, new Realm(key, max, levels, next, trib));
        }
    }

    public static Realm get(String name) {
        return realms.get(name);
    }
} 
