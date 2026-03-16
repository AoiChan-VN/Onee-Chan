package aoi.aoichan.engine.module;

/**
 * Module interface
 *
 * Tất cả gameplay systems sẽ implement interface này.
 *
 * Ví dụ:
 * - StatsModule
 * - SkillModule
 * - ClassModule
 */
public interface Module {

    String getName();

    void enable();

    void disable();

} 
