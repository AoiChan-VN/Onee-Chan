package aoichan.crystal.infrastructure.util;

import aoichan.crystal.bootstrap.AoiMain;

public class LogUtil {

    public static void info(String message) {

        AoiMain.get().getLogger().info(message);

    }

    public static void warn(String message) {

        AoiMain.get().getLogger().warning(message);

    }

}
