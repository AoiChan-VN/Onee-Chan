package vn.aoi.onii.skill;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.*;

public class SkillConfigManager {

    private final Map<String, SkillConfig> configs = new HashMap<>();

    public void load(File file) {
        try {
            Gson gson = new Gson();
            Type type = new TypeToken<List<SkillConfig>>(){}.getType();

            List<SkillConfig> list = gson.fromJson(new FileReader(file), type);

            for (SkillConfig cfg : list) {
                if (cfg.id == null) continue;
                if (cfg.cooldown < 0) cfg.cooldown = 0;
                configs.put(cfg.id, cfg);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public SkillConfig get(String id) {
        return configs.get(id);
    }
}
