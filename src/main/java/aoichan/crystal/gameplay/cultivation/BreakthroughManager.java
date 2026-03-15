package aoichan.crystal.gameplay.cultivation;

import aoichan.crystal.infrastructure.broadcast.BroadcastManager;
import org.bukkit.entity.Player;

public class BreakthroughManager {

    public static void breakthrough(Player player,
                                    CultivationProfile profile,
                                    String nextRealm) {

        // 【!】Code: broadcast thiên kiếp
        player.getServer().broadcastMessage(
                "§5⚡ Thiên Kiếp: " + player.getName() +
                " đang đột phá cảnh giới!"
        );

        // 【!】Code: set realm mới
        profile.setRealm(nextRealm);

        BroadcastManager.successBell(player);

        player.sendMessage(
                "§aBạn đã đột phá thành công!"
        );

    }

} 
