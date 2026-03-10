package aoichan.crystal.gameplay.forge;

import aoichan.crystal.api.forge.ForgeResult;
import aoichan.crystal.engine.forge.ForgeEngine;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ForgeManager {

    // [!] Code: Active forge sessions
    private final Map<UUID, ForgeEngine> sessions = new HashMap<>();

    private final ForgeEngine engine = new ForgeEngine();

    // [!] Code: Start forge
    public ForgeResult forge(Player player, ItemStack item, ItemStack gem, double rate) {

        return engine.forge(item, gem, rate);
    }
}
