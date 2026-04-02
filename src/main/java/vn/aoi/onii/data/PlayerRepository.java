package vn.aoi.onii.data;

import org.bukkit.Bukkit;
import vn.aoi.onii.Main;
import java.sql.*;

public class PlayerRepository {

    public static void save(PlayerData d){
        Bukkit.getScheduler().runTaskAsynchronously(Main.get(),()->{
            try{
                PreparedStatement ps=Main.get().db().get()
                        .prepareStatement("REPLACE INTO players VALUES(?,?,?,?)");
                ps.setString(1,d.uuid.toString());
                ps.setString(2,d.realm);
                ps.setInt(3,d.level);
                ps.setInt(4,d.exp);
                ps.executeUpdate();
            }catch(Exception e){e.printStackTrace();}
        });
    }
}
