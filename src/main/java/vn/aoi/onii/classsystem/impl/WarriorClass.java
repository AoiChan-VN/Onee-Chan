package vn.aoi.onii.classsystem.impl;

import org.bukkit.attribute.Attribute;
import vn.aoi.onii.classsystem.*;

public class WarriorClass implements PlayerClass {

    @Override
    public String getId() { return "warrior"; }

    @Override
    public String getName() { return "Warrior"; }

    @Override
    public void onSelect(ClassContext ctx) {
        ctx.getPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(24.0);
    }

    @Override
    public void onRemove(ClassContext ctx) {
        ctx.getPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20.0);
    }
}
