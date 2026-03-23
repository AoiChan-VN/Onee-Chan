package vn.aoi.onii.skill.config;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

public class SkillConfigManager {

    private final Map<String, SkillConfig> configs = new HashMap<>();

    public void load(File file) {
        configs.clear(); // FIX reload

        try {
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                try (Writer w = new FileWriter(file)) {
                    w.write("[]");
                }
            }

            try (Reader reader = new FileReader(file)) {
                Type type = new TypeToken<List<SkillConfig>>(){}.getType();
                List<SkillConfig> list = new Gson().fromJson(reader, type);

                if (list != null) {
                    for (SkillConfig cfg : list) {
                        configs.put(cfg.id.toLowerCase(), cfg);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
