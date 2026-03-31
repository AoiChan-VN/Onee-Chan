package vn.aoi.onii.rank;

import org.bukkit.configuration.ConfigurationSection;
import vn.aoi.onii.AoiMain;
import java.util.*;

public class RankManager {

    private final Map<String, Rank> ranks = new HashMap<>();
    private final AoiMain plugin;

    public RankManager(AoiMain plugin){this.plugin=plugin;}

    public void load(){
        ConfigurationSection s=plugin.getConfig().getConfigurationSection("ranks");
        for(String k:s.getKeys(false)){
            ConfigurationSection r=s.getConfigurationSection(k);
            Rank rank=new Rank(k,r.getInt("max-level"),r.getString("next-rank"),r.getBoolean("is-thien-kiep",false));
            if(r.isConfigurationSection("levels")){
                for(String l:r.getConfigurationSection("levels").getKeys(false)){
                    int lv=Integer.parseInt(l);
                    int exp=r.getInt("levels."+l+".exp");
                    rank.addLevel(lv,exp);
                }
            }else rank.setFlatExp(r.getInt("exp-required"));
            ranks.put(k,rank);
        }
    }

    public Rank get(String name){return ranks.get(name);} }
