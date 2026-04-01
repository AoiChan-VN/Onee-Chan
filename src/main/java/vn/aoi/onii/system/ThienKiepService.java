package vn.aoi.onii.system;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import vn.aoi.onii.AoiMain;

import java.util.Random;

public class ThienKiepService {

    private final AoiMain plugin;
    private final Random r = new Random();

    public ThienKiepService(AoiMain plugin){this.plugin=plugin;}

    public void start(Player p){
        new BukkitRunnable(){
            int t=0;
            public void run(){
                if(!p.isOnline()){cancel();return;}
                Location loc=p.getLocation().add(r.nextInt(6)-3,0,r.nextInt(6)-3);
                p.getWorld().strikeLightning(loc);
                t++;
                if(t>=30){
                    if(!p.isDead()) p.sendMessage("§a渡劫 thành công!");
                    else p.sendMessage("§c渡劫 thất bại!");
                    cancel();
                }
            }
        }.runTaskTimer(plugin,0,20);
    }
}
