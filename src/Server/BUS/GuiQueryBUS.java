/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server.BUS;

import Server.DAO.GuiQueryDAO;
import Shares.DTO.NguoiDungDTO;
import java.util.ArrayList;

/**
 *
 * @author NGAN DOAN
 */
public class GuiQueryBUS {

    public ArrayList<NguoiDungDTO> listnguoidung;
    public ArrayList<NguoiDungDTO> userinfo;
    public ArrayList<NguoiDungDTO> listuser;

    public ArrayList<NguoiDungDTO> readnguoidung() {
        GuiQueryDAO data = new GuiQueryDAO();
        listnguoidung = data.readNguoiDung();
        return listnguoidung;
    }

    public ArrayList<NguoiDungDTO> readdsnguoidung() {
        GuiQueryDAO data = new GuiQueryDAO();
        listuser = data.readDsNguoiDung();
        return listuser;
    }

    public ArrayList<NguoiDungDTO> readinfo() {
        GuiQueryDAO data = new GuiQueryDAO();
        userinfo = data.readInfoNguoiDung();
        return userinfo;
    }

    public boolean isBlockUser(NguoiDungDTO nguoi) {
        if (new GuiQueryDAO().isBlock(nguoi)) {
            return true;
        }
        return false;
    }
}
