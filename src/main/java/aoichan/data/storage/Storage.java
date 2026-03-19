package aoichan.data.storage;

import aoichan.data.model.PlayerData;

import java.util.UUID;

public interface Storage {

    PlayerData load(UUID uuid);

    void save(PlayerData data);
}
 
