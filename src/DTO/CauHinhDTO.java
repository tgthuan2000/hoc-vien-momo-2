package DTO;

public class CauHinhDTO {

    // fix cứng id
    private final int id = 1;
    private int soLuongCauHoi = 0, diemTranDau = 0;

    public CauHinhDTO() {
    }

    public CauHinhDTO(int soLuongCauHoi, int diemTranDau) {
        this.soLuongCauHoi = soLuongCauHoi;
        this.diemTranDau = diemTranDau;
    }

    public int getSoLuongCauHoi() {
        return soLuongCauHoi;
    }

    public void setSoLuongCauHoi(int soLuongCauHoi) {
        this.soLuongCauHoi = soLuongCauHoi;
    }

    public int getDiemTranDau() {
        return diemTranDau;
    }

    public void setDiemTranDau(int diemTranDau) {
        this.diemTranDau = diemTranDau;
    }

    // lấy id để cập nhật
    public int getId() {
        return id;
    }
}
