/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BUS;

import static Client.Login.txtUser;
import static Client.Login.txtPass;
import DTO.NguoiDungDTO;
import Server.ServerHocVienMomo.DAO.LoginDAO;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author thanh
 */
public class LoginBUS {
    LoginDAO dao ;
    public ArrayList<NguoiDungDTO> nguoiDungs;

    public LoginBUS() {
        dao = new LoginDAO();
    }
    
    public ArrayList<NguoiDungDTO> docdulieu(){
        NguoiDungDTO data= new NguoiDungDTO();
        try {
            nguoiDungs = dao.docguoiDung();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "List not found");
        }
        return nguoiDungs;
    }
    
    public boolean kiemTra(NguoiDungDTO nguoiDungDTO){
//        boolean flag = true;
//        if(!txtUser.getText().equals("") && !txtPass.getText().equals("") ){
            for(NguoiDungDTO nd :docdulieu()){
              if(nd.getUsername().equals(nguoiDungDTO.getUsername()) && nd.getPassword().equals(getMd5(nguoiDungDTO.getPassword())))
              { 
                  return true ;
              }
            }
//        }else{
//            flag = false;
//            JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin");
//        }
        return false;
    }
    
    public static String getMd5(String input)
    {
        try {
  
            // Static getInstance method is called with hashing MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
  
            // digest() method is called to calculate message digest
            //  of an input digest() return array of byte
            byte[] messageDigest = md.digest(input.getBytes());
  
            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);
  
            // Convert message digest into hex value
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        } 
  
        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
