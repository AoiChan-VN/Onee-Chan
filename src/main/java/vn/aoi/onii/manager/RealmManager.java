package vn.aoi.onii.manager;

import org.bukkit.configuration.ConfigurationSection;
import vn.aoi.onii.config.ConfigManager;
import vn.aoi.onii.model.Realm;

import java.util.*;

public class RealmManager {

    private final Map<String, Realm> realms = new HashMap<>();
    private final Map<String, String> originalNames = new LinkedHashMap<>();
    
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
                    .broadcast(r.getBoolean("tribulation.broadcast", false))
                    .strikes(r.getInt("tribulation.strikes", 3))
                    .damage(r.getDouble("tribulation.damage", 4.0))
                    .interval(r.getInt("tribulation.interval", 40))
                    .levels(levels)
                    .build();

            String lowerKey = key.toLowerCase(Locale.ROOT);
            realms.put(lowerKey, realm);
            originalNames.put(lowerKey, key);
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

            double exp = lvlSection.getDouble("exp", 0);
            String phase = lvlSection.getString("phase", "NONE");

            levels.put(level, Realm.LevelData.builder()
                    .expRequired(exp)
                    .phase(phase)
                    .build());
        }

        return levels;
    }

    public Realm getRealm(String name) {
        if (name == null) return null;
        return realms.get(name.toLowerCase(Locale.ROOT));
    }

    public boolean exists(String name) {
        if (name == null) return false;
        return realms.containsKey(name.toLowerCase(Locale.ROOT));
    }

    public Set<String> getAllRealms() {
        return new HashSet<>(originalNames.values());
    }
}
