package aoi.aoichan.module;

/*
 Interface module cho CrystalEngine
 Các plugin Phase sau sẽ implement cái này
*/

public interface EngineModule {

    void onLoad();

    void onEnable();

    void onDisable();

    String getName();
} 
