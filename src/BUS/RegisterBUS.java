/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BUS;

import static Client.Register.txtDate;
import static Client.Register.txtEmail;
import static Client.Register.txtPass;
import static Client.Register.txtPassAgain;
import static Client.Register.txtUser;
import DTO.NguoiDungDTO;
import Server.ServerHocVienMomo.DAO.RegisterDAO;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

/**
 *
 * @author thanh
 */
public class RegisterBUS {

    RegisterDAO dao;
    public ArrayList<NguoiDungDTO> nguoiDungs;

    public RegisterBUS() {
        dao = new RegisterDAO();
    }

    public ArrayList<NguoiDungDTO> docdulieu() {
        NguoiDungDTO data = new NguoiDungDTO();
        try {
            nguoiDungs = dao.docguoiDung();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "List not found");
        }
        return nguoiDungs;
    }

    public boolean ktTrung(NguoiDungDTO nguoiDung) {

        for (NguoiDungDTO nd : docdulieu()) {
            if (nd.getUsername().equals(nguoiDung.getUsername())) {
                JOptionPane.showMessageDialog(null, "Đã tồn tại username");
                return false;
            }
        }
        return true;
    }

    public boolean ktThemNguoiDung() {
        boolean flag = true;
        if (!txtUser.getText().equals("") && !txtEmail.getText().equals("") && !txtPass.getText().equals("") && !txtPassAgain.getText().equals("")) {
            String regex = "^([_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*(\\.[a-zA-Z]{1,6}))?$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(txtEmail.getText().trim());
            if (matcher.matches()) {
                if (txtPass.getText().equals(txtPassAgain.getText())) {
                    if (txtPass.getText().length() >= 5) {
                        try {
                            new SimpleDateFormat("yyyy-MM-dd").format(txtDate.getDate()).trim();
                            flag = true;
                        } catch (Exception e) {
                            flag = false;
                            JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin");
                        }
                    } else {
                        flag = false;
                        JOptionPane.showMessageDialog(null, "Mật khẩu phải từ 5 ký tự trở lên");
                    }
                } else {
                    flag = false;
                    JOptionPane.showMessageDialog(null, "Mật khẩu không khớp");
                }
            } else {
                flag = false;
                JOptionPane.showMessageDialog(null, "User name là email (abc@abc.com)");
            }
        } else {
            flag = false;
            JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin");
        }
        return flag;
    }

    public boolean ThemNguoiDung(NguoiDungDTO nguoiDungDTO) {
        if (dao.Them(nguoiDungDTO)) {
            return true;
        }
        return false;
    }
}
