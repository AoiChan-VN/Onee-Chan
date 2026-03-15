package aoi.aoichan.api;

import aoi.aoichan.AoiMain;
import aoi.aoichan.core.EngineCore;

public class EngineAPI {

    // 【!】Code: lấy engine core
    public static EngineCore getEngine(){
        return AoiMain.getInstance().getEngineCore();
    }

}
