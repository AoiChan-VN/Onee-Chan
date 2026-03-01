package aoichan.crystal.utils;

import org.bukkit.ChatColor;

import java.util.List;
import java.util.stream.Collectors;

public class ColorUtil {

    public static String colorize(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static List<String> colorizeList(List<String> list) {
        return list.stream()
                .map(ColorUtil::colorize)
                .collect(Collectors.toList());
    }
}
