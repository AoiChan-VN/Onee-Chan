package vn.aoi.onii.service;

import org.bukkit.entity.Player;
import vn.aoi.onii.service.ExpService;

public class CultivationService {

    private final ExpService expService;

    public CultivationService(ExpService expService) {
        this.expService = expService;
    }

    public void addExp(Player player, double amount) {
        expService.addExp(player, amount);
    }
}
