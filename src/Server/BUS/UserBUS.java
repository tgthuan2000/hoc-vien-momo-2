package Server.BUS;

import Server.DAO.UserDAO;
import Shares.DTO.NguoiDungDTO;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class UserBUS {

    private final UserDAO dao;
    public ArrayList<NguoiDungDTO> nguoiDungs;

    public UserBUS() {
        dao = new UserDAO();
    }

    public ArrayList<NguoiDungDTO> docdulieu() {
        try {
            nguoiDungs = dao.docNguoiDung();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "List not found");
        }
        return nguoiDungs;
    }

    public boolean kiemTra(String email) {
        for (NguoiDungDTO nd : docdulieu()) {
            if (nd.getUsername().toLowerCase().equals(email.toLowerCase())) {
                return false;
            }
        }
        return true;
    }

    public boolean ThemNguoiDung(NguoiDungDTO nguoiDungDTO) {
        return dao.Them(nguoiDungDTO);
    }

    public boolean SuaIQ(String user, String iq) {
        return dao.suaIQ(user, Integer.parseInt(iq));
    }

    public NguoiDungDTO login(String email, String password) {
        return dao.dangnhap(email, password);
    }

    public boolean capNhatDiem(NguoiDungDTO nd, int diem, String trangThai) {
        return dao.capNhatDiem(nd, diem, trangThai);
    }

    public NguoiDungDTO getNewInfo(NguoiDungDTO user) {
        return dao.getInfo(user.getUsername());
    }

}
