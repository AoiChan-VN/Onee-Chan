package aoichan.crystal.gameplay.skill;

import aoichan.crystal.bootstrap.CrystalPlugin;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class SkillManager {

    private static final Map<String, Skill> skills = new HashMap<>();

    public static void load() {

        File file = new File(
                CrystalPlugin.get().getDataFolder(),
                "skills.yml"
        );

        if (!file.exists()) {
            CrystalPlugin.get().saveResource("skills.yml", false);
        }

        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

        skills.clear();

        for (String id : config.getKeys(false)) {

            String name = config.getString(id + ".name");

            SkillType type = SkillType.valueOf(
                    config.getString(id + ".type").toUpperCase()
            );

            double mana = config.getDouble(id + ".mana");

            int cooldown = config.getInt(id + ".cooldown");

            double scale = config.getDouble(id + ".damage-scale");

            Skill skill = new Skill(
                    id,
                    name,
                    type,
                    mana,
                    cooldown,
                    scale
            );

            skills.put(id, skill);

        }

    }

    public static Skill getSkill(String id) {

        return skills.get(id);

    }

}
 
