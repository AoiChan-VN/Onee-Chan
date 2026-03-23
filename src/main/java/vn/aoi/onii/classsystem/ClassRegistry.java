package vn.aoi.onii.classsystem;

import java.util.*;

public class ClassRegistry {

    private final Map<String, PlayerClass> classes = new HashMap<>();

    public void register(PlayerClass clazz) {
        classes.put(clazz.getId().toLowerCase(), clazz);
    }

    public PlayerClass get(String id) {
        return classes.get(id.toLowerCase());
    }

    public Collection<PlayerClass> all() {
        return classes.values();
    }
} 
