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

/**
 *
 * @author thanh
 */
public class LoginDAO {
    Connect my = new Connect("localhost", "sa", "123", "hoc_vien_momo");
    
    public LoginDAO(){}
    
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
}
