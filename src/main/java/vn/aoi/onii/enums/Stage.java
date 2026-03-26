package vn.aoi.onii.enums;

public enum Stage {
    NHAP_MON("Nhập Môn"),
    SO_KY("Sơ Kỳ"),
    TRUNG_KY("Trung Kỳ"),
    HAU_KY("Hậu Kỳ"),
    DINH_PHONG("Đỉnh Phong");

    private final String display;

    Stage(String display) {
        this.display = display;
    }

    public String getDisplay() {
        return display;
    }
} 
