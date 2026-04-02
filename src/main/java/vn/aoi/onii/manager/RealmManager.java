package vn.aoi.onii.manager;

import vn.aoi.onii.config.ConfigManager;
import java.util.*;

public class RealmManager {

    private static final Map<String,Object> map=new HashMap<>();

    public static void load(){
        map.clear();
        var sec=ConfigManager.realms.getConfigurationSection("realms");
        if(sec!=null) for(String k:sec.getKeys(false)) map.put(k,new Object());
    }

    public static boolean exist(String name){
        return map.containsKey(name);
    }
}
