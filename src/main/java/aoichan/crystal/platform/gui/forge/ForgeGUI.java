package aoichan.crystal.platform.gui.forge;

import aoichan.crystal.platform.gui.base.GUI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class ForgeGUI extends GUI {

    @Override
    public void open(Player player) {

        player.openInventory(create());
    }

    @Override
    public Inventory create() {

        return Bukkit.createInventory(
                null,
                27,
                "Crystal Forge"
        );
    }
}
