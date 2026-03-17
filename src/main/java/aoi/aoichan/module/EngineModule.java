package aoi.aoichan.module;

import aoi.aoichan.service.ServiceRegistry;

/**
 * Contract cho tất cả module
 */
public interface EngineModule {

    void onLoad(ServiceRegistry registry);

    void onUnload();
} 
