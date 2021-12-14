package Shares.DTO;

public class NguoiDungDTO implements Comparable<NguoiDungDTO> {

    private int tongTran = 0, tongTranThang = 0, chuoiThang = 0, chuoiThua = 0, chuoiThangMax = 0, chuoiThuaMax = 0, tongDiem = 0, diemIQ = 0, xephang = 0;
    private String username, password, tenNguoiDung, ngaySinh;
    //  gioiTinh: true - nam, false - nữ
    //  trangThaiChuoi: true - thắng, false - thua
    private boolean isBlock = false, gioiTinh, trangThaiChuoi;

    public NguoiDungDTO() {
    }

    public NguoiDungDTO(NguoiDungDTO nd) {
        this.tenNguoiDung = nd.tenNguoiDung;
        this.chuoiThangMax = nd.chuoiThangMax;
        this.chuoiThuaMax = nd.chuoiThuaMax;
        this.tongTranThang = nd.tongTranThang;
        this.tongTran = nd.tongTran;
        this.tongDiem = nd.tongDiem;
        this.xephang = nd.xephang;
    }

    public NguoiDungDTO(String username, String password, String tenNguoiDung, boolean gioiTinh, String ngaySinh) {
        this.username = username;
        this.password = password;
        this.tenNguoiDung = tenNguoiDung;
        this.gioiTinh = gioiTinh;
        this.ngaySinh = ngaySinh;
    }

    public NguoiDungDTO(String tenNguoiDung, int tranThangMax, int tranThuaMax, int tongTranThang, int tongTran, int tongDiem) {
        this.tenNguoiDung = tenNguoiDung;
        this.chuoiThangMax = tranThangMax;
        this.chuoiThuaMax = tranThuaMax;
        this.tongTranThang = tongTranThang;
        this.tongTran = tongTran;
        this.tongDiem = tongDiem;
    }

    public NguoiDungDTO(String tenNguoiDung, int tranThangMax, int tranThuaMax, int tongTranThang, int tongTran, int tongDiem, int xephang) {
        this.tenNguoiDung = tenNguoiDung;
        this.chuoiThangMax = tranThangMax;
        this.chuoiThuaMax = tranThuaMax;
        this.tongTranThang = tongTranThang;
        this.tongTran = tongTran;
        this.tongDiem = tongDiem;
        this.xephang = xephang;
    }

    public NguoiDungDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public int getTongTran() {
        return tongTran;
    }

    public void setTongTran(int tongTran) {
        this.tongTran = tongTran;
    }

    public int getTongTranThang() {
        return tongTranThang;
    }

    public void setTongTranThang(int tongTranThang) {
        this.tongTranThang = tongTranThang;
    }

    public int getChuoiThang() {
        return chuoiThang;
    }

    public void setChuoiThang(int chuoiThang) {
        this.chuoiThang = chuoiThang;
    }

    public int getChuoiThua() {
        return chuoiThua;
    }

    public void setChuoiThua(int chuoiThua) {
        this.chuoiThua = chuoiThua;
    }

    public int getChuoiThangMax() {
        return chuoiThangMax;
    }

    public void setChuoiThangMax(int chuoiThangMax) {
        this.chuoiThangMax = chuoiThangMax;
    }

    public int getChuoiThuaMax() {
        return chuoiThuaMax;
    }

    public void setChuoiThuaMax(int chuoiThuaMax) {
        this.chuoiThuaMax = chuoiThuaMax;
    }

    public int getTongDiem() {
        return tongDiem;
    }

    public void setTongDiem(int tongDiem) {
        this.tongDiem = tongDiem;
    }

    public int getDiemIQ() {
        return diemIQ;
    }

    public void setDiemIQ(int diemIQ) {
        this.diemIQ = diemIQ;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTenNguoiDung() {
        return tenNguoiDung;
    }

    public void setTenNguoiDung(String tenNguoiDung) {
        this.tenNguoiDung = tenNguoiDung;
    }

    public boolean isIsBlock() {
        return isBlock;
    }

    public void setIsBlock(boolean isBlock) {
        this.isBlock = isBlock;
    }

    public boolean getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(boolean gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public boolean isTrangThaiChuoi() {
        return trangThaiChuoi;
    }

    public void setTrangThaiChuoi(boolean trangThaiChuoi) {
        this.trangThaiChuoi = trangThaiChuoi;
    }

    public int getXephang() {
        return xephang;
    }

    public void setXephang(int xephang) {
        this.xephang = xephang;
    }

    @Override
    public int compareTo(NguoiDungDTO o) {
        int a = o.getTongDiem();
        return a - this.tongDiem;
    }

}
