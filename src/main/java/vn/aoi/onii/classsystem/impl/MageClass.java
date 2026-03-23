package vn.aoi.onii.classsystem.impl;

import org.bukkit.entity.Player;
import vn.aoi.onii.classsystem.*;

public class MageClass implements PlayerClass {

    @Override
    public String getId() { return "mage"; }

    @Override
    public String getName() { return "Mage"; }

    @Override
    public void onSelect(ClassContext ctx) {
        Player p = ctx.getPlayer();
        p.setWalkSpeed(0.25f);
    }

    @Override
    public void onRemove(ClassContext ctx) {
        ctx.getPlayer().setWalkSpeed(0.2f);
    }
} 
