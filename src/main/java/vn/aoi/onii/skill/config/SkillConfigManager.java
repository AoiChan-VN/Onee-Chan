public class SkillConfigManager {

    private final Map<String, SkillConfig> configs = new HashMap<>();

    public void load(File file) {
        configs.clear();

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
                        if (cfg.id != null) {
                            configs.put(cfg.id.toLowerCase(), cfg);
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public SkillConfig get(String id) {
        if (id == null) return null;
        return configs.get(id.toLowerCase());
    }
}
