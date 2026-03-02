package aoichan.crystal.core;

import aoichan.crystal.AoiMain;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class RefineSoundManager {

    private final AoiMain plugin;

    public RefineSoundManager(AoiMain plugin) {
        this.plugin = plugin;
    }

    public void playSuccess(Player player, String quality, int level) {

        if (level >= plugin.getConfig()
                .getInt("refine.thunder.min-level", 7)) {
            playThunder(player);
        } else {
            player.playSound(player.getLocation(),
                    Sound.ENTITY_PLAYER_LEVELUP, 1f, 1.2f);
        }
    }

    public void playFail(Player player, boolean highTier) {

        if (highTier) {
            playThunder(player);
        } else {
            player.playSound(player.getLocation(),
                    Sound.BLOCK_ANVIL_BREAK, 1f, 1f);
        }
    }

    public void playThunder(Player player) {

        player.getWorld()
                .strikeLightningEffect(player.getLocation());

        player.playSound(player.getLocation(),
                Sound.ENTITY_LIGHTNING_BOLT_THUNDER,
                3f,
                0.6f);
    }

    public void playExplosion(Player player) {

        player.playSound(player.getLocation(),
                Sound.ENTITY_GENERIC_EXPLODE,
                2f,
                0.7f);
    }
}
