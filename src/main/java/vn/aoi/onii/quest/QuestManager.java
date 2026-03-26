package vn.aoi.onii.quest;

import org.bukkit.configuration.file.YamlConfiguration;
import vn.aoi.onii.Main;

import java.io.File;
import java.util.*;

public class QuestManager {

    private final YamlConfiguration config;
    private final Map<UUID, Map<String, Integer>> progress = new HashMap<>();

    public QuestManager() {
        File file = new File(Main.getInstance().getDataFolder(), "quests.yml");
        if (!file.exists()) Main.getInstance().saveResource("quests.yml", false);
        config = YamlConfiguration.loadConfiguration(file);
    }

    public YamlConfiguration getConfig() {
        return config;
    }

    public void addProgress(UUID uuid, String quest, int amount) {
        progress.putIfAbsent(uuid, new HashMap<>());
        progress.get(uuid).merge(quest, amount, Integer::sum);
    }

    public int getProgress(UUID uuid, String quest) {
        return progress.getOrDefault(uuid, new HashMap<>()).getOrDefault(quest, 0);
    }

    public void reset(UUID uuid, String quest) {
        if (progress.containsKey(uuid)) {
            progress.get(uuid).remove(quest);
        }
    }
} 
