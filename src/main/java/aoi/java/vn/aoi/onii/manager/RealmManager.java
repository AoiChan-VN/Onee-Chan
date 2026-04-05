package vn.aoi.onii.manager;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import vn.aoi.onii.model.Realm;

import java.util.HashMap;
import java.util.Map;

public class RealmManager {

    private final Map<String, Realm> realms = new HashMap<>();

    public RealmManager(FileConfiguration config) {
        load(config);
    }

    private void load(FileConfiguration config) {
        ConfigurationSection section = config.getConfigurationSection("realms");

        if (section == null) return;

        for (String key : section.getKeys(false)) {
            ConfigurationSection r = section.getConfigurationSection(key);

            Map<Integer, Realm.LevelData> levels = new HashMap<>();

            if (r.contains("levels")) {
                for (String lvl : r.getConfigurationSection("levels").getKeys(false)) {
                    int level = Integer.parseInt(lvl);

                    double exp = r.getDouble("levels." + lvl + ".exp");
                    String phase = r.getString("levels." + lvl + ".phase");

                    levels.put(level, Realm.LevelData.builder()
                            .expRequired(exp)
                            .phase(phase)
                            .build());
                }
            }

            Realm realm = Realm.builder()
                    .name(key)
                    .nextRank(r.getString("next-rank"))
                    .maxLevel(r.getInt("max-level"))
                    .tribulation(r.getBoolean("is-tribulation", false))
                    .duration(r.getInt("duration", 30))
                    .levels(levels)
                    .build();

            realms.put(key, realm);
        }
    }
    
    public Realm getRealm(String name) {
        return realms.get(name);
    }

    public boolean exists(String name) {
        return realms.containsKey(name);
    }

    public java.util.Set<String> getAllRealms() {
        return realms.keySet();
    }
} 
