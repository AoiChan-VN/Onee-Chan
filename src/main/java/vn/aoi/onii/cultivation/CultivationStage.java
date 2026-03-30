package vn.aoi.onii.cultivation;

public enum CultivationStage {
    PHAM_NHAN(0),
    LUYEN_KHI(100),
    TRUC_CO(500),
    KIM_DAN(1500),
    NGUYEN_ANH(5000),
    HOA_THAN(15000);

    private final int required;

    CultivationStage(int required) {
        this.required = required;
    }

    public int getRequired() { return required; }
} 
