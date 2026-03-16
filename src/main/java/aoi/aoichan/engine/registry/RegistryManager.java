package aoi.aoichan.engine.registry;

import aoi.aoichan.engine.util.EngineLogger;

import java.util.HashMap;
import java.util.Map;

/**
 * RegistryManager
 *
 * Registry system lưu trữ data global:
 *
 * Ví dụ:
 * - Skill registry
 * - Item registry
 * - Class registry
 */
public class RegistryManager {

    private final Map<String, Object> registries = new HashMap<>();

    public void initialize() {
        EngineLogger.info("RegistryManager initialized.");
    }

    public void register(String key, Object registry) {
        registries.put(key, registry);
    }

    public Object get(String key) {
        return registries.get(key);
    }

} 
