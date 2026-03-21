// ========================= USAGE EXAMPLE =========================
package aoichan.example;

import aoichan.service.player.PlayerSessionManager;
import aoichan.service.player.PlayerSession;

import java.util.UUID;

/**
 * 【❅】 Example usage inside other module
 */
public class ExampleUsage {

    private final PlayerSessionManager sessionManager;

    public ExampleUsage(PlayerSessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    public void addStrength(UUID uuid, int amount) {
        PlayerSession session = sessionManager.require(uuid);

        session.getData().set("rpg.str", 
            session.getData().getInt("rpg.str") + amount
        );

        sessionManager.markDirty(uuid);
    }
}  
