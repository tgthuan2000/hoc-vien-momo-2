/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

/**
 *
 * @author NGAN DOAN
 */
public class CauHinhDTO {

    private final int id = 1;
    private int soLuongCauHoi = 0, diemTranDau = 0, thoiGian = 0;

    public CauHinhDTO() {
    }

    public CauHinhDTO(int soLuongCauHoi, int diemTranDau, int thoiGian) {
        this.soLuongCauHoi = soLuongCauHoi;
        this.diemTranDau = diemTranDau;
        this.thoiGian = thoiGian;
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

    public int getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(int thoiGian) {
        this.thoiGian = thoiGian;
    }

    // lấy id để cập nhật
    public int getId() {
        return id;
    }
    
}
