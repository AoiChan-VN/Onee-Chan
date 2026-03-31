package vn.aoi.onii.player;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import vn.aoi.onii.AoiMain;

import java.sql.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerManager {

    private final AoiMain plugin;
    private final Map<UUID, PlayerData> cache = new ConcurrentHashMap<>();

    public PlayerManager(AoiMain plugin){this.plugin=plugin;}

    public void load(Player p){
        Bukkit.getScheduler().runTaskAsynchronously(plugin,()->{
            try{
                PreparedStatement ps=plugin.getDatabase().get().prepareStatement("SELECT * FROM players WHERE uuid=?");
                ps.setString(1,p.getUniqueId().toString());
                ResultSet rs=ps.executeQuery();

                PlayerData d;
                if(rs.next()){
                    d=new PlayerData(rs.getString("rank"),rs.getInt("level"),rs.getInt("exp"));
                }else{
                    d=new PlayerData("Phàm nhân",1,0);
                    saveSync(p.getUniqueId(),d);
                }
                cache.put(p.getUniqueId(),d);
            }catch(Exception e){e.printStackTrace();}
        });
    }

    public void save(UUID id){
        PlayerData d=cache.get(id);
        if(d==null)return;
        Bukkit.getScheduler().runTaskAsynchronously(plugin,()->saveSync(id,d));
    }

    private void saveSync(UUID id,PlayerData d){
        try{
            PreparedStatement ps=plugin.getDatabase().get().prepareStatement("REPLACE INTO players VALUES(?,?,?,?)");
            ps.setString(1,id.toString());
            ps.setString(2,d.getRank());
            ps.setInt(3,d.getLevel());
            ps.setInt(4,d.getExp());
            ps.executeUpdate();
        }catch(Exception e){e.printStackTrace();}
    }

    public PlayerData get(Player p){return cache.get(p.getUniqueId());}
    public void shutdown(){cache.forEach(this::save);} }
