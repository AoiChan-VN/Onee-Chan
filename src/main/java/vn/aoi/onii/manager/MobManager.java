package vn.aoi.onii.manager;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;
import vn.aoi.onii.config.ConfigManager;

import java.util.HashMap;
import java.util.Map;

public class MobManager {

    private final Map<EntityType, Double> mobExp = new HashMap<>();
    private final ConfigManager configManager;

    public MobManager(ConfigManager configManager) {
        this.configManager = configManager;
        load();
    }

    public void reload() {
        mobExp.clear();
        load();
    }

    private void load() {
        ConfigurationSection section = configManager.getConfig().getConfigurationSection("mobs-exp");

        if (section == null) return;

        for (String key : section.getKeys(false)) {
            try {
                EntityType type = EntityType.valueOf(key.toUpperCase());
                double exp = section.getDouble(key);

                mobExp.put(type, exp);
            } catch (IllegalArgumentException ignored) {
                // mob sai tên ➺ bỏ qua
            }
        }
    }

    public double getExp(EntityType type) {
        return mobExp.getOrDefault(type, 0.0);
    }
}
