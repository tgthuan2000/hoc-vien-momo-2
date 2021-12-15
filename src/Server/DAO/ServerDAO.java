package Server.DAO;

import Shares.DTO.CauHinhDTO;
import Shares.DTO.CauHoiDTO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class ServerDAO {

    private final Connect mysql;

    public ServerDAO() {
        mysql = new Connect();
    }

    public CauHinhDTO readCauhinh() {
        CauHinhDTO cauhinh = new CauHinhDTO();
        try {
            String qry = "SELECT * FROM CauHinh";
            PreparedStatement ps = mysql.getPreparedStatement(qry);
            ResultSet rs = mysql.executeQuery();
            while (rs.next()) {
                cauhinh.setSoLuongCauHoi(rs.getInt("SoLuongCauHoi"));
                cauhinh.setDiemTranDau(rs.getInt("DiemTranDau"));
                cauhinh.setThoiGian(rs.getInt("ThoiGian"));
            }
            mysql.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cauhinh;
    }

    public ArrayList<CauHoiDTO> readCauhoi() {
        ArrayList<CauHoiDTO> list = new ArrayList<CauHoiDTO>();
        try {
            String qry = "SELECT * FROM CauHoi";
            PreparedStatement ps = mysql.getPreparedStatement(qry);
            ResultSet rs = mysql.executeQuery();
            while (rs.next()) {
                CauHoiDTO kh = new CauHoiDTO();
                kh.setId(rs.getInt(1));
                kh.setCauHoi(rs.getString(2));
                kh.setCauDung(rs.getString(3));
                kh.setCauSai1(rs.getString(4));
                kh.setCauSai2(rs.getString(5));
                kh.setCauSai3(rs.getString(6));
                list.add(kh);
            }
            mysql.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean change(CauHinhDTO con) {
        String sql = "UPDATE CauHinh SET SoLuongCauHoi=?,DiemTranDau=?,ThoiGian=? WHERE Id=?";
        try {
            PreparedStatement ps = mysql.getPreparedStatement(sql);

            ps.setInt(1, con.getSoLuongCauHoi());
            ps.setInt(2, con.getDiemTranDau());
            ps.setInt(3, con.getThoiGian());
            ps.setInt(4, con.getId());

            return mysql.excuteUpdate();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "CHANGE DB ERROR");

            e.printStackTrace();
        }
        return false;
    }

    public boolean changecauhoi(CauHoiDTO cauhoi) {
        String sql = "UPDATE CauHoi SET CauHoi=?,CauDung=?,CauSai1=?,CauSai2=?,CauSai3=? WHERE Id=?";

        try {
            PreparedStatement ps = mysql.getPreparedStatement(sql);
            ps.setString(1, cauhoi.getCauHoi());
            ps.setString(2, cauhoi.getCauDung());
            ps.setString(3, cauhoi.getCauSai1());
            ps.setString(4, cauhoi.getCauSai2());
            ps.setString(5, cauhoi.getCauSai3());
            ps.setInt(6, cauhoi.getId());
            return mysql.excuteUpdate();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "CHANGE DB ERROR");
            e.printStackTrace();
        }

        return false;
    }

    public boolean add(CauHoiDTO cauhoi) {
        String sql = "INSERT INTO CauHoi(CauHoi,CauDung,CauSai1,CauSai2,CauSai3)" + "VALUES(?,?,?,?,?)";
        try {
            PreparedStatement ps = mysql.getPreparedStatement(sql);
            ps.setString(1, cauhoi.getCauHoi());
            ps.setString(2, cauhoi.getCauDung());
            ps.setString(3, cauhoi.getCauSai1());
            ps.setString(4, cauhoi.getCauSai2());
            ps.setString(5, cauhoi.getCauSai3());

            return mysql.excuteUpdate();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ADD DB ERROR");
            e.printStackTrace();
        }
        return false;
    }

}
