package vn.aoi.onii.skill;

import java.util.*;

public class SkillRegistry {

    private final Map<String, Skill> skills = new HashMap<>();

    public void register(Skill skill) {
        skills.put(skill.getId().toLowerCase(), skill);
    }

    public Skill get(String id) {
        return skills.get(id.toLowerCase());
    }
}
