package vn.aoi.onii.player;

public class PlayerData {

    private String name;
    private String canhGioi;
    private String tuVi;

    public PlayerData(String name, String canhGioi, String tuVi) {
        this.name = name;
        this.canhGioi = canhGioi;
        this.tuVi = tuVi;
    }

    public String getName() { return name; }
    public String getCanhGioi() { return canhGioi; }
    public String getTuVi() { return tuVi; }

    public void setCanhGioi(String canhGioi) { this.canhGioi = canhGioi; }
    public void setTuVi(String tuVi) { this.tuVi = tuVi; }
}
