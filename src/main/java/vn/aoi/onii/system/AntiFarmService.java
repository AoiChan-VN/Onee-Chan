package vn.aoi.onii.system;

import org.bukkit.entity.EntityType;
import java.util.*;

public class AntiFarmService {

    private final Map<UUID, Map<EntityType, Integer>> killMap = new HashMap<>();

    public int apply(UUID id, EntityType type, int baseExp) {
        killMap.putIfAbsent(id, new HashMap<>());
        Map<EntityType,Integer> map = killMap.get(id);

        int count = map.getOrDefault(type, 0) + 1;
        map.put(type, count);

        if(count > 20) return baseExp / 4;
        if(count > 10) return baseExp / 2;
        return baseExp;
    }
} 
