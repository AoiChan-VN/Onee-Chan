package vn.aoi.onii.system;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import vn.aoi.onii.AoiChanPlugin;

import java.util.Random;

public class DoKiepService {

    private final AoiChanPlugin plugin;
    private final Random random = new Random();

    public DoKiepService(AoiChanPlugin plugin){this.plugin=plugin;}

    public void start(Player p){
        new BukkitRunnable(){
            int ticks=0;
            @Override
            public void run(){
                if(!p.isOnline()){cancel();return;}

                Location loc=p.getLocation().clone().add(random.nextInt(6)-3,0,random.nextInt(6)-3);
                p.getWorld().strikeLightning(loc);

                ticks++;
                if(ticks>=30){
                    if(p.isDead()){
                        p.sendMessage("§cĐộ kiếp thất bại!");
                    }else{
                        p.sendMessage("§aĐộ kiếp thành công!");
                    }
                    cancel();
                }
            }
        }.runTaskTimer(plugin,0,20);
    }
}
