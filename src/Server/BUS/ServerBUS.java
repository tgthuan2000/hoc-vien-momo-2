package Server.BUS;

import Server.DAO.ServerDAO;
import Shares.DTO.CauHinhDTO;
import Shares.DTO.CauHoiDTO;
import java.util.ArrayList;

public class ServerBUS {

    public ServerBUS() {
    }

    public CauHinhDTO conf;
    public ArrayList<CauHoiDTO> dsch;

    public CauHinhDTO readConfig() {
        ServerDAO data = new ServerDAO();
        conf = data.readCauhinh();
        return conf;
    }

    public boolean changeconfig(CauHinhDTO con) {
        if (new ServerDAO().change(con)) {
            return true;
        }
        return false;
    }

    public boolean changecauhoi(CauHoiDTO cau) {
        if (new ServerDAO().changecauhoi(cau)) {
            return true;
        }
        return false;
    }

    public ArrayList<CauHoiDTO> readcauhoi() {
        ServerDAO data = new ServerDAO();
        dsch = data.readCauhoi();

        return dsch;
    }

    public boolean addcauhoi(CauHoiDTO cauhoi) {
        if (new ServerDAO().add(cauhoi)) {
            return readcauhoi().add(cauhoi);
        }
        return false;
    }
}
