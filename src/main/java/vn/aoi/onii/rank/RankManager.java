package vn.aoi.onii.rank;

import org.bukkit.configuration.ConfigurationSection;
import vn.aoi.onii.AoiMain;

import java.util.*;

public class RankManager {

    private final AoiMain plugin;
    private final Map<String, Rank> ranks = new HashMap<>();

    public RankManager(AoiMain plugin) {
        this.plugin = plugin;
    }

    public void load() {
        ConfigurationSection section = plugin.getConfig().getConfigurationSection("ranks");

        for (String key : section.getKeys(false)) {
            ConfigurationSection r = section.getConfigurationSection(key);

            Rank rank = new Rank(
                    key,
                    r.getInt("max-level"),
                    r.getString("next-rank"),
                    r.getBoolean("is-do-kiep", false)
            );

            if (r.isConfigurationSection("levels")) {
                ConfigurationSection lv = r.getConfigurationSection("levels");

                for (String l : lv.getKeys(false)) {
                    int level = Integer.parseInt(l);
                    int exp = lv.getInt(l + ".exp");
                    String phase = lv.getString(l + ".phase");

                    rank.addLevel(level, exp, phase);
                }
            } else {
                rank.setFlatExp(r.getInt("exp-required"));
            }

            ranks.put(key, rank);
        }
    }

    public Rank get(String name) {
        return ranks.get(name);
    }
} 
