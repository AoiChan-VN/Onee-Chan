package aoi.aoichan.command;

import aoi.aoichan.AoiMain;

/*
 Quản lý command hệ thống
*/

public class CommandManager {

    private final AoiMain plugin;

    public CommandManager(AoiMain plugin) {
        this.plugin = plugin;
    }

    // 【!】Code: đăng ký command
    public void registerCommands() {

        plugin.getCommand("crystal").setExecutor(new CrystalCommand(plugin));

    }

} 
