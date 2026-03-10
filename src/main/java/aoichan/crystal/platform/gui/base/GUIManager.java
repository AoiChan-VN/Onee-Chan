package aoichan.crystal.platform.gui.base;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GUIManager {

    // [!] Code: Player open GUI
    private final Map<UUID, GUI> open = new HashMap<>();

    public void open(Player player, GUI gui) {

        player.openInventory(gui.create());

        open.put(player.getUniqueId(), gui);
    }

    public GUI get(Player player) {

        return open.get(player.getUniqueId());
    }
} 
