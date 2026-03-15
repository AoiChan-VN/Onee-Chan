package aoi.aoichan.core;

import java.util.logging.Logger;

/*
 * Logger riêng cho CrystalEngine
 */

public class EngineLogger {

    private final Logger logger;

    public EngineLogger(Logger logger) {
        this.logger = logger;
    }

    public void info(String msg) {
        logger.info("[CrystalEngine] " + msg);
    }

    public void warn(String msg) {
        logger.warning("[CrystalEngine] " + msg);
    }

    public void error(String msg) {
        logger.severe("[CrystalEngine] " + msg);
    }
} 
