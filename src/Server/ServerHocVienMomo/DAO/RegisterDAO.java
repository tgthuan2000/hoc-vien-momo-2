/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server.ServerHocVienMomo.DAO;

import DTO.NguoiDungDTO;
import Server.ServerHocVienMomo.ConnectDB.Connect;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author thanh
 */
public class RegisterDAO {
    Connect my = new Connect("localhost", "sa", "123", "hoc_vien_momo");
    
    public RegisterDAO(){}
    
    //lấy dữ liệu sản phẩm
    public ArrayList<NguoiDungDTO> docguoiDung() throws Exception{
        ArrayList<NguoiDungDTO> list = new ArrayList<>();
        try {
        String sql="SELECT * FROM NguoiDung";
        PreparedStatement ps = my.getPreparedStatement(sql);
        ResultSet rs =my.executeQuery();
        while(rs.next()){
            NguoiDungDTO nd= new NguoiDungDTO();
            nd.setUsername(rs.getString("Username"));
            nd.setPassword(rs.getString("Password"));
            nd.setTenNguoiDung(rs.getString("TenNguoiDung"));
            nd.setGioiTinh(rs.getBoolean("GioiTinh"));
            nd.setNgaySinh(String.valueOf(rs.getDate("NgaySinh")));
            list.add(nd);
        }
            
        } catch (Exception e) {
//            e.printStackTrace();
        }
       return list;
    }
    
    //thêm dữ liêu uoi dung
    public boolean Them(NguoiDungDTO user){
     
        String sql="INSERT INTO NguoiDung(Username,Password,TenNguoiDung,GioiTinh,NgaySinh,TongTran,TongTranThang,ChuoiThangMax,ChuoiThuaMax,ChuoiThang,ChuoiThua,TrangThaiChuoi,TongDiem,DiemIQ,Block)"
                +"VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)" ;
        try {
            PreparedStatement ps =my.getPreparedStatement(sql);
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
            //JOptionPane.showMessageDialog(null, "Insert Error");
//            e.printStackTrace();
        }
        return false;
    }
}
