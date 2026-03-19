package aoichan.service.player;

import aoichan.data.model.PlayerData;

public class PlayerSession {

    private final PlayerData data;
    private boolean dirty;

    public PlayerSession(PlayerData data) {
        this.data = data;
    }

    public PlayerData getData() {
        return data;
    }

    public boolean isDirty() {
        return dirty;
    }

    public void markDirty() {
        dirty = true;
    }

    public void clean() {
        dirty = false;
    }
} 
