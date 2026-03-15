package com.aoi.crystalengine.api;

import com.aoi.crystalengine.CrystalEngine;
import com.aoi.crystalengine.player.PlayerManager;
import com.aoi.crystalengine.storage.DataStorage;
import com.aoi.crystalengine.api.service.ServiceManager;

/*
#【!】Code:
Public API để plugin khác truy cập CrystalEngine
*/

public class CrystalAPI {

    public static PlayerManager getPlayerManager() {
        return CrystalEngine.get().getPlayerManager();
    }

    public static DataStorage getStorage() {
        return CrystalEngine.get().getDataStorage();
    }

    public static ServiceManager getServiceManager() {
        return CrystalEngine.get().getServiceManager();
    }
}
