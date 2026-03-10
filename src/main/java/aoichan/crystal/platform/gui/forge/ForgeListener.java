package aoichan.crystal.platform.gui.forge;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ForgeListener implements Listener {

    private final ForgeButton forgeButton;

    public ForgeListener(ForgeButton forgeButton) {
        this.forgeButton = forgeButton;
    }

    @EventHandler
    public void click(InventoryClickEvent event) {

        if (!(event.getWhoClicked() instanceof Player player)) return;

        if (!event.getView().getTitle()
                .equals("§5Crystal Forge")) return;

        int slot = event.getRawSlot();

        if (slot == ForgeGUI.BUTTON_SLOT) {

            event.setCancelled(true);

            forgeButton.click(
                    player,
                    event.getInventory()
            );
        }
    }
} 
