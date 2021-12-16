package Server.DAO;

import Shares.DTO.NguoiDungDTO;
import Shares.Key;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class UserDAO {

    private final Connect my;

    public UserDAO() {
        my = new Connect();
    }

    // kiểm tra đăng nhập // kt tk đã đăng nhập chưa
    public NguoiDungDTO dangnhap(String username, String pasword) {
        NguoiDungDTO nd = new NguoiDungDTO();
        try {
            String sql = "SELECT * FROM NguoiDung WHERE Username = ? AND Password = ?";
            PreparedStatement ps = my.getPreparedStatement(sql);
            ps.setString(1, username);
            ps.setString(2, pasword);

            ResultSet rs = my.executeQuery();
            while (rs.next()) {
                nd.setUsername(rs.getString("Username"));
                nd.setIsBlock(rs.getBoolean("Block"));
                nd.setTenNguoiDung(rs.getString("TenNguoiDung"));
                nd.setChuoiThang(rs.getInt("ChuoiThang"));
                nd.setChuoiThangMax(rs.getInt("ChuoiThangMax"));
                nd.setChuoiThua(rs.getInt("ChuoiThua"));
                nd.setChuoiThuaMax(rs.getInt("ChuoiThuaMax"));
                nd.setDiemIQ(rs.getInt("DiemIQ"));
                nd.setGioiTinh(rs.getBoolean("GioiTinh"));
                nd.setNgaySinh(String.valueOf(rs.getDate("NgaySinh")));
                nd.setTongDiem(rs.getInt("TongDiem"));
                nd.setTongTran(rs.getInt("TongTran"));
                nd.setTongTranThang(rs.getInt("TongTranThang"));
            }
            my.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nd;
    }

    // lấy dữ liệu người dùng
    public ArrayList<NguoiDungDTO> docNguoiDung() {
        ArrayList<NguoiDungDTO> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM NguoiDung";
            PreparedStatement ps = my.getPreparedStatement(sql);
            ResultSet rs = my.executeQuery();
            while (rs.next()) {
                NguoiDungDTO nd = new NguoiDungDTO();
                nd.setUsername(rs.getString("Username"));
                nd.setPassword(rs.getString("Password"));
                nd.setTenNguoiDung(rs.getString("TenNguoiDung"));
                nd.setGioiTinh(rs.getBoolean("GioiTinh"));
                nd.setNgaySinh(String.valueOf(rs.getDate("NgaySinh")));
                nd.setChuoiThangMax(rs.getInt("ChuoiThangMax"));
                nd.setChuoiThuaMax(rs.getInt("ChuoiThuaMax"));
                nd.setTongTran(rs.getInt("TongTran"));
                nd.setTongTranThang(rs.getInt("TongTranThang"));
                nd.setTongDiem(rs.getInt("TongDiem"));
                list.add(nd);
            }
            my.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // thêm người dùng
    public boolean Them(NguoiDungDTO user) {
        String sql = "INSERT INTO NguoiDung(Username,Password,TenNguoiDung,GioiTinh,NgaySinh,TongTran,TongTranThang,ChuoiThangMax,ChuoiThuaMax,ChuoiThang,ChuoiThua,TrangThaiChuoi,TongDiem,DiemIQ,Block)"
                + " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps = my.getPreparedStatement(sql);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getTenNguoiDung());
            ps.setBoolean(4, user.getGioiTinh());
            ps.setString(5, user.getNgaySinh());
            ps.setInt(6, 0);
            ps.setInt(7, 0);
            ps.setInt(8, 0);
            ps.setInt(9, 0);
            ps.setInt(10, 0);
            ps.setInt(11, 0);
            ps.setBoolean(12, false);
            ps.setInt(13, 0);
            ps.setInt(14, 0);
            ps.setBoolean(15, false);
            return my.excuteUpdate();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Insert Error");
            e.printStackTrace();
        }
        return false;
    }

    public boolean capNhatDiem(NguoiDungDTO nd, int diem, String trangThai) {
        String sql = "UPDATE NguoiDung SET "
                + "TongDiem = TongDiem + ?, "
                + "TongTran = TongTran + 1, ";
        switch (trangThai) {
            case Key.WINER:
                sql += "ChuoiThang = ChuoiThang + 1, "
                        + "TongTranThang = TongTranThang + 1, "
                        + "ChuoiThua = 0 ";
                break;
            case Key.LOSER:
                sql += "ChuoiThua = ChuoiThua + 1, "
                        + "ChuoiThang = 0 ";
                break;
        }
        sql += "WHERE Username = ?";

        try {
            PreparedStatement ps = my.getPreparedStatement(sql);
            ps.setInt(1, diem);
            ps.setString(2, nd.getUsername());
            return my.excuteUpdate();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Update Error");
            e.printStackTrace();
        }
        return false;
    }

    public boolean suaIQ(String username, int IQ) {
        String sql = "UPDATE NguoiDung  SET DiemIQ= ? WHERE Username= ?";
        try {
            PreparedStatement ps = my.getPreparedStatement(sql);
            ps.setInt(1, IQ);
            ps.setString(2, username);
            return my.excuteUpdate();
        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, "Lỗi sửa IQ");
        }
        return false;
    }
}
