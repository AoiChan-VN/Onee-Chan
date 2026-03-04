public interface StorageProvider {

    // code của: khởi tạo database
    void init();

    // code của: lưu dữ liệu
    void saveData(java.util.UUID uuid, int gems);

    // code của: load dữ liệu
    int loadData(java.util.UUID uuid);

    // code của: đóng kết nối
    void close();
}
