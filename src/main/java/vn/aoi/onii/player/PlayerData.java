package vn.aoi.onii.player;

import vn.aoi.onii.enums.Realm;
import vn.aoi.onii.enums.Stage;

import java.util.UUID;

public class PlayerData {

    private UUID uuid;
    private String name;
    private Realm realm;
    private Stage stage;
    private String sect;

    public PlayerData(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
        this.realm = Realm.PHAM_NHAN;
        this.stage = Stage.SO_KY;
        this.sect = "Không có";
    }

    public UUID getUuid() { return uuid; }
    public String getName() { return name; }

    public Realm getRealm() { return realm; }
    public void setRealm(Realm realm) { this.realm = realm; }

    public Stage getStage() { return stage; }
    public void setStage(Stage stage) { this.stage = stage; }

    public String getSect() { return sect; }
    public void setSect(String sect) { this.sect = sect; }
}
