package vn.aoi.onii.system;

import org.bukkit.entity.Player;
import vn.aoi.onii.AoiMain;
import vn.aoi.onii.player.PlayerData;
import vn.aoi.onii.rank.Rank;

public class ExpService {

    private final AoiMain plugin;

    public ExpService(AoiMain plugin){this.plugin=plugin;}

    public void addExp(Player p,int amount){
        PlayerData d=plugin.getPlayerManager().get(p);
        if(d==null)return;

        d.addExp(amount);
        Rank r=plugin.getRankManager().get(d.getRank());

        if(d.getExp()>=r.getExp(d.getLevel())){
            d.setLevel(d.getLevel()+1);
            d.addExp(-r.getExp(d.getLevel()-1));

            if(d.getLevel()>r.getMax()){
                if(r.isThienKiep()){
                    plugin.getThienKiepService().start(p);
                }else{
                    d.setRank(r.next());
                    d.setLevel(1);
                }
            }
        }
    }
}
