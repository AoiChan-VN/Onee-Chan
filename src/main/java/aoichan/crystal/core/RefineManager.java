package aoichan.crystal.core;

import aoichan.crystal.AoiMain;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class RefineManager {

    private final AoiMain plugin;
    private final Random random = new Random();

    public RefineManager(AoiMain plugin) {
        this.plugin = plugin;
    }

    public void refine(Player player,
                       ItemStack gem,
                       ItemStack item,
                       String qualityKey,
                       int level,
                       double successRate) {

        FileConfiguration cfg = plugin.getRefineConfig();

        int delay = cfg.getInt("refine.delay-ticks");

        Bukkit.getScheduler().runTaskLater(plugin, () -> {

            announce(player, "§6【Thiên Đạo】§eLôi kiếp sắp đến...");

            boolean success = random.nextDouble() <= successRate;

            if (success) {

                gem.setAmount(0);

                announce(player, "§aDung hợp thành công!");

                playSuccessSound(player, qualityKey, level);

                return;
            }

            gem.setAmount(0);

            double breakRate = cfg.getDouble(
                    "qualities." + qualityKey + ".item-break-rate");

            boolean highTier = level >= cfg.getInt("refine.thunder.min-level");

            if (highTier && random.nextDouble() <= breakRate) {

                item.setAmount(0);

                Bukkit.broadcastMessage("§4Thần Binh của "
                        + player.getName()
                        + " đã bị phá hủy!");

                player.playSound(player.getLocation(),
                        Sound.ENTITY_GENERIC_EXPLODE,
                        2f, 0.7f);

                return;
            }

            announce(player, "§cDung hợp thất bại.");

            player.playSound(player.getLocation(),
                    Sound.BLOCK_ANVIL_BREAK,
                    1f, 1f);

        }, delay);
    }

    private void announce(Player player, String msg) {

        String mode = plugin.getRefineConfig()
                .getString("refine.broadcast-mode");

        switch (mode.toUpperCase()) {
            case "SERVER" -> Bukkit.broadcastMessage(msg);
            case "PERSONAL" -> player.sendMessage(msg);
            default -> {}
        }
    }

    private void playSuccessSound(Player player,
                                  String qualityKey,
                                  int level) {

        FileConfiguration cfg = plugin.getRefineConfig();

        String minQuality = cfg.getString("refine.thunder.min-quality");
        int minLevel = cfg.getInt("refine.thunder.min-level");

        if (qualityKey.equalsIgnoreCase(minQuality)
                || level >= minLevel) {

            float volume = (float) cfg.getDouble("refine.thunder.volume");
            float pitch = (float) cfg.getDouble("refine.thunder.pitch");

            player.getWorld().strikeLightningEffect(player.getLocation());
            player.playSound(player.getLocation(),
                    Sound.ENTITY_LIGHTNING_BOLT_THUNDER,
                    volume,
                    pitch);
        } else {
            player.playSound(player.getLocation(),
                    Sound.ENTITY_PLAYER_LEVELUP,
                    1f,
                    1.2f);
        }
    }
}
