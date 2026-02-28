package aoidev.crystal.bootstrap;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public final class ConsoleBanner {

    private ConsoleBanner() {}

    public static void print(Plugin plugin) {

        String banner =
                "\n" +
                "██████╗  █████╗ ███╗   ███╗███████╗\n" +
                "██╔══██╗██╔══██╗████╗ ████║██╔════╝\n" +
                "██████╔╝███████║██╔████╔██║█████╗  \n" +
                "██╔══██╗██╔══██║██║╚██╔╝██║██╔══╝  \n" +
                "██║  ██║██║  ██║██║ ╚═╝ ██║███████╗\n" +
                "╚═╝  ╚═╝╚═╝  ╚═╝╚═╝     ╚═╝╚══════╝\n";

        plugin.getLogger().info(banner);
        plugin.getLogger().info("Gems Ultimate Production Core");
        plugin.getLogger().info("Server: " + Bukkit.getName() + " " + Bukkit.getVersion());
        plugin.getLogger().info("Plugin Version: " + plugin.getDescription().getVersion());

        Runtime rt = Runtime.getRuntime();
        long mb = 1024 * 1024;
        plugin.getLogger().info("Memory: " +
                (rt.freeMemory() / mb) + "MB free / " +
                (rt.totalMemory() / mb) + "MB total / " +
                (rt.maxMemory() / mb) + "MB max");

        plugin.getLogger().info("==============================================");
    }
}
