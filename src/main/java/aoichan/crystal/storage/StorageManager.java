package aoichan.crystal.storage;

import aoichan.crystal.AoiMain;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

public class StorageManager {

    // code của: giữ instance storage duy nhất
    private static StorageProvider provider;

    // code của: khởi tạo storage khi plugin enable
    public static void init() {
        FileConfiguration config = AoiMain.getInstance().getConfig();
        String type = config.getString("storage.type", "SQLITE");

        try {

            // code của: chọn loại storage theo config
            if (type.equalsIgnoreCase("MYSQL")) {
                provider = new MySQLStorage();
                Bukkit.getConsoleSender().sendMessage("§a[Crystal] Using MySQL storage.");
            } else {
                provider = new SQLiteStorage();
                Bukkit.getConsoleSender().sendMessage("§a[Crystal] Using SQLite storage.");
            }

            // code của: khởi tạo database (create table nếu chưa có)
            provider.init();

        } catch (Exception ex) {

            // code của: fallback nếu MySQL lỗi
            Bukkit.getConsoleSender().sendMessage("§c[Crystal] Storage failed. Falling back to SQLite.");
            ex.printStackTrace();

            provider = new SQLiteStorage();
            provider.init();
        }
    }

    // code của: reload storage không restart server
    public static void reload() {

        // code của: đóng kết nối cũ
        shutdown();

        // code của: khởi tạo lại
        init();
    }

    // code của: trả về storage đang dùng
    public static StorageProvider get() {
        return provider;
    }

    // code của: đóng database khi plugin disable
    public static void shutdown() {
        if (provider != null) {
            provider.close();
        }
    }
} 
