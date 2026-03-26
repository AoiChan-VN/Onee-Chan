package vn.aoi.onii.enums;

public enum Realm {
    PHAM_NHAN("Phàm Nhân"),
    LUYEN_THE("Luyện Thể"),
    LUYEN_KHI("Luyện Khí"),
    TRUC_CO("Trúc Cơ"),
    KIM_DAN("Kim Đan"),
    NGUYEN_ANH("Nguyên Anh"),
    HOA_THAN("Hóa Thần"),
    LUYEN_HU("Luyện Hư"),
    HOP_THE("Hợp Thể"),
    DAI_THUA("Đại Thừa"),
    DO_KIEP("Độ Kiếp"),
    DAI_DE("Đại Đế"),
    TIEN_TON("Tiên Tôn"),
    TIEN_VUONG("Tiên Vương"),
    TIEN_HOANG("Tiên Hoàng"),
    TIEN_DE("Tiên Đế");

    private final String display;

    Realm(String display) {
        this.display = display;
    }

    public String getDisplay() {
        return display;
    }
} 
