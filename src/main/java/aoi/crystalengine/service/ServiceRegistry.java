package com.aoi.crystalengine.service;

import java.util.HashMap;
import java.util.Map;

/*
#【!】Code:
Service registry cho API cross-plugin.
*/

public class ServiceRegistry {

    private final Map<Class<?>, Object> services = new HashMap<>();

    public <T> void register(Class<T> clazz, T service) {

        services.put(clazz, service);
    }

    public <T> T get(Class<T> clazz) {

        return clazz.cast(services.get(clazz));
    }

} 
