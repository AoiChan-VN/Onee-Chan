package vn.aoi.onii.manager;

import org.bukkit.configuration.ConfigurationSection;
import vn.aoi.onii.model.Realm;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class RealmManager {

    private final Map<String, Realm> realms = new HashMap<>();
    private final ConfigManager configManager;

    public RealmManager(ConfigManager configManager) {
        this.configManager = configManager;
        load();
    }

    public void reload() {
        realms.clear();
        load();
    }

    private void load() {
        ConfigurationSection section = configManager.getRealms().getConfigurationSection("realms");
        if (section == null) return;

        for (String key : section.getKeys(false)) {
            ConfigurationSection r = section.getConfigurationSection(key);
            if (r == null) continue;

            Map<Integer, Realm.LevelData> levels = loadLevels(r.getConfigurationSection("levels"));

            Realm realm = Realm.builder()
                    .name(key)
                    .nextRank(r.getString("next-rank", null))
                    .maxLevel(r.getInt("max-level", levels.size()))
                    .tribulation(r.getBoolean("is-tribulation", false))
                    .duration(r.getInt("duration", 30))
                    .levels(levels)
                    .build();

            realms.put(key, realm);
        }
    }

    private Map<Integer, Realm.LevelData> loadLevels(ConfigurationSection section) {
        Map<Integer, Realm.LevelData> levels = new HashMap<>();
        if (section == null) return levels;

        for (String lvlKey : section.getKeys(false)) {
            ConfigurationSection lvlSection = section.getConfigurationSection(lvlKey);
            if (lvlSection == null) continue;

            int level;
            try {
                level = Integer.parseInt(lvlKey);
            } catch (NumberFormatException e) {
                continue;
            }

            levels.put(level, Realm.LevelData.builder()
                    .expRequired(lvlSection.getDouble("exp", 0))
                    .phase(lvlSection.getString("phase", "NONE"))
                    .build());
        }

        return levels;
    }

    public Realm getRealm(String name) {
        return realms.get(name);
    }

    public boolean exists(String name) {
        return realms.containsKey(name);
    }

    public Set<String> getAllRealms() {
        return realms.keySet();
    }
}
