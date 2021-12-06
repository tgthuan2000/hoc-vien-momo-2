package DTO;

public class CauHoiDTO {

    private int id;
    private String cauHoi, cauDung;
    private String[] danhSachCauSai;

    public CauHoiDTO() {
    }

    public CauHoiDTO(int id, String cauHoi, String cauDung, String[] cauSai) {
        this.id = id;
        this.cauHoi = cauHoi;
        this.cauDung = cauDung;
        this.danhSachCauSai = cauSai;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCauDung() {
        return cauDung;
    }

    public void setCauDung(String cauDung) {
        this.cauDung = cauDung;
    }

    public String[] getDanhSachCauSai() {
        return danhSachCauSai;
    }

    public void setDanhSachCauSai(String[] cauSai) {
        this.danhSachCauSai = cauSai;
    }

    public String getCauHoi() {
        return cauHoi;
    }

    public void setCauHoi(String cauHoi) {
        this.cauHoi = cauHoi;
    }

}
