package vn.aoi.onii.api;

import vn.aoi.onii.manager.PlayerManager;
import vn.aoi.onii.model.Cultivator;

import java.util.UUID;

public class AoiAPI {

    private static AoiAPI instance;

    private final PlayerManager playerManager;

    public AoiAPI(PlayerManager playerManager) {
        this.playerManager = playerManager;
        instance = this;
    }

    public static AoiAPI get() {
        return instance;
    }

    public Cultivator getCultivator(UUID uuid) {
        return playerManager.get(uuid);
    }

    public String getRealm(UUID uuid) {
        Cultivator c = getCultivator(uuid);
        return c != null ? c.getRealm() : null;
    }

    public int getLevel(UUID uuid) {
        Cultivator c = getCultivator(uuid);
        return c != null ? c.getLevel() : 0;
    }
}
