package aoichan.crystal.infrastructure.config;

import aoichan.crystal.bootstrap.CrystalPlugin;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class GUIConfig {

    private final YamlConfiguration config;

    public GUIConfig() {

        File file = new File(CrystalPlugin.get().getDataFolder(), "gui.yml");

        if (!file.exists()) {
            CrystalPlugin.get().saveResource("gui.yml", false);
        }

        config = YamlConfiguration.loadConfiguration(file);
    }

    // [!] Code: GUI title
    public String title() {

        return config.getString("forge.title");
    }

    // [!] Code: GUI size
    public int size() {

        return config.getInt("forge.size");
    }

    // [!] Code: Item slot
    public int itemSlot() {

        return config.getInt("forge.slots.item");
    }

    // [!] Code: Gem slot
    public int gemSlot() {

        return config.getInt("forge.slots.gem");
    }

    // [!] Code: Forge button
    public int buttonSlot() {

        return config.getInt("forge.slots.button");
    }
} 
