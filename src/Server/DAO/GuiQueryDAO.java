/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server.DAO;

import Shares.DTO.NguoiDungDTO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author NGAN DOAN
 */
public class GuiQueryDAO {

    private final Connect mysql;

    public GuiQueryDAO() {
        mysql = new Connect();
    }

    public ArrayList<NguoiDungDTO> readNguoiDung() {
        ArrayList<NguoiDungDTO> list = new ArrayList<NguoiDungDTO>();
        try {
            String qry = "SELECT * FROM NguoiDung";
            PreparedStatement ps = mysql.getPreparedStatement(qry);
            ResultSet rs = mysql.executeQuery();
            while (rs.next()) {
                NguoiDungDTO nguoidung = new NguoiDungDTO();
                nguoidung.setTongTran(rs.getInt("TongTran"));
                nguoidung.setTongTranThang(rs.getInt("TongTranThang"));
                nguoidung.setChuoiThangMax(rs.getInt("ChuoiThangMax"));
                list.add(nguoidung);
            }
            mysql.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public ArrayList<NguoiDungDTO> readDsNguoiDung() {
        ArrayList<NguoiDungDTO> list = new ArrayList<NguoiDungDTO>();
        try {
            String qry = "SELECT Username FROM NguoiDung";
            PreparedStatement ps = mysql.getPreparedStatement(qry);
            ResultSet rs = mysql.executeQuery();
            while (rs.next()) {
                NguoiDungDTO nguoidung = new NguoiDungDTO();
                nguoidung.setUsername(rs.getString("Username"));
                list.add(nguoidung);
            }
            mysql.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public ArrayList<NguoiDungDTO> readInfoNguoiDung() {
        ArrayList<NguoiDungDTO> list = new ArrayList<NguoiDungDTO>();
        try {
            String qry = "SELECT * FROM NguoiDung";
            PreparedStatement ps = mysql.getPreparedStatement(qry);
            ResultSet rs = mysql.executeQuery();
            while (rs.next()) {
                NguoiDungDTO nguoidung = new NguoiDungDTO();
                nguoidung.setUsername(rs.getString("Username"));
                nguoidung.setTongTran(rs.getInt("TongTran"));
                nguoidung.setTongTranThang(rs.getInt("TongTranThang"));
                nguoidung.setChuoiThangMax(rs.getInt("ChuoiThangMax"));
                nguoidung.setTenNguoiDung(rs.getString("TenNguoiDung"));
                nguoidung.setGioiTinh(rs.getBoolean("GioiTinh"));
                nguoidung.setIsBlock(rs.getBoolean("Block"));
                list.add(nguoidung);

            }
            mysql.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public boolean isBlock(NguoiDungDTO nguoi) {
        String sql = "UPDATE NguoiDung SET Block=? WHERE Username=?";
        try {
            PreparedStatement ps = mysql.getPreparedStatement(sql);

            ps.setBoolean(1, nguoi.isIsBlock());
            ps.setString(2, nguoi.getUsername());
            return mysql.excuteUpdate();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "CHANGE DB ERROR");

            e.printStackTrace();
        }
        return false;
    }
}
