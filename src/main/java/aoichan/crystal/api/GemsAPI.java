package aoichan.crystal.api;

import aoichan.crystal.core.GemsManager;
import aoichan.crystal.core.SocketManager;
import org.bukkit.inventory.ItemStack;

import java.util.Set;

public class GemsAPI {

    private final GemsManager gemsManager;
    private final SocketManager socketManager;

    public GemsAPI(GemsManager gm, SocketManager sm) {
        this.gemsManager = gm;
        this.socketManager = sm;
    }

    public Set<String> getAllGems() {
        return gemsManager.getAllGems();
    }

    public ItemStack createGemItem(String id) {
        return gemsManager.buildGem(id);
    }

    public void socket(ItemStack item, String gemId) {
        socketManager.attachGem(item, gemId);
    }
}
