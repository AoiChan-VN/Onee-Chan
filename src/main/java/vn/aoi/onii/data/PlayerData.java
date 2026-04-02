package vn.aoi.onii.data;

import java.util.UUID;

public class PlayerData {

    public UUID uuid;
    public String realm;
    public int level;
    public int exp;

    public PlayerData(UUID u,String r,int l,int e){
        uuid=u;realm=r;level=l;exp=e;
    }
}
