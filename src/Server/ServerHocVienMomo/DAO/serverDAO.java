/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server.ServerHocVienMomo.DAO;

import DTO.CauHinhDTO;
import DTO.CauHoiDTO;
import Server.ServerHocVienMomo.ConnectDB.Connect;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author NGAN DOAN
 */
public class serverDAO {

    Connect mysql = new Connect("localhost", "sa", "ngan2000", "hoc_vien_momo");

    public serverDAO() {
    }

    public ArrayList<CauHinhDTO> readCauhinh() {
        ArrayList<CauHinhDTO> list = new ArrayList<CauHinhDTO>();
        try {
            String qry = "SELECT * FROM CauHinh";
            PreparedStatement ps = mysql.getPreparedStatement(qry);
            ResultSet rs = mysql.executeQuery();
            while (rs.next()) {
                CauHinhDTO cauhinh = new CauHinhDTO();
                cauhinh.setSoLuongCauHoi(rs.getInt("SoLuongCauHoi"));
                cauhinh.setDiemTranDau(rs.getInt("DiemTranDau"));
                cauhinh.setThoiGian(rs.getInt("ThoiGian"));
                list.add(cauhinh);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
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

    public boolean add(CauHoiDTO cauhoi) {
        String sql = "INSERT INTO CauHoi(CauHoi,CauDung,CauSai1,CauSai2,CauSai3)" + "VALUES(?,?,?,?,?)";
        try {
            PreparedStatement ps = mysql.getPreparedStatement(sql);
//            ps.setInt(1,cauhoi.getId());
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

    public static void main(String[] args) {
        serverDAO dao = new serverDAO();
        System.out.println("lala" + dao.readCauhinh());
    }
}
