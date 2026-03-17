package aoi.aoichan.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Service container của engine
 * Dùng để inject dependency runtime
 */
public class ServiceRegistry {

    private final Map<Class<?>, Object> services = new ConcurrentHashMap<>();

    /**
     * Register service
     */
    public <T> void register(Class<T> clazz, T instance) {
        services.put(clazz, instance);
    }

    /**
     * Get service
     */
    @SuppressWarnings("unchecked")
    public <T> T get(Class<T> clazz) {
        Object obj = services.get(clazz);

        if (obj == null) {
            throw new IllegalStateException("Service not found: " + clazz.getName());
        }

        return (T) obj;
    }

    /**
     * Check service
     */
    public boolean has(Class<?> clazz) {
        return services.containsKey(clazz);
    }
} 
