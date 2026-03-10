package aoichan.crystal.api;

import aoichan.crystal.core.item.GemItemBuilder;
import aoichan.crystal.core.gem.Gem;
import aoichan.crystal.core.gem.GemRegistry;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

// [!] Code: API implementation
public class GemServiceImpl implements GemService {

    @Override
    public void giveGem(
            Player player,
            String gemId,
            int level
    ) {

        Gem gem =
                GemRegistry.get(gemId);

        if (gem == null)
            return;

        // [!] Code: create gem item
        ItemStack item =
                GemItemBuilder.buildGem(
                        gem,
                        level
                );

        player.getInventory().addItem(item);
    }

    @Override
    public void takeGem(
            Player player,
            String gemId
    ) {

        // simple remove example
        player.getInventory()
                .remove(
                        org.bukkit.Material.EMERALD
                );
    }

    @Override
    public boolean hasGem(
            Player player,
            String gemId
    ) {

        return player.getInventory()
                .contains(
                        org.bukkit.Material.EMERALD
                );
    }
} 
