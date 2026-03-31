package vn.aoi.onii.rank;

import org.bukkit.configuration.ConfigurationSection;
import vn.aoi.onii.Main;

import java.util.HashMap;
import java.util.Map;

public class RankManager {

    private final Map<String, ConfigurationSection> ranks = new HashMap<>();

    public RankManager(Main plugin) {
        ConfigurationSection section = plugin.getConfig().getConfigurationSection("ranks");
        for (String key : section.getKeys(false)) {
            ranks.put(key, section.getConfigurationSection(key));
        }
    }

    public ConfigurationSection getRank(String name) {
        return ranks.get(name);
    }
} 
