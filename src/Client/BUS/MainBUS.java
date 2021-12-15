package Client.BUS;

import Client.Status;
import Shares.DTO.NguoiDungDTO;
import Shares.Key;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainBUS {

    public MainBUS() {
    }

    public int playgame() {
        try {
            BUS.writeLine(Key.PLAY_GAME);
            BUS.flush();
            System.out.println("Chờ game...");
            return Status.OK;
        } catch (IOException ex) {
            return Status.LOI_KETNOI_SERVER;
        }
    }

    public int canclegame() {
        try {
            BUS.writeLine(Key.CANCLE_GAME);
            BUS.flush();
            System.out.println("Huỷ chờ");
            return Status.OK;
        } catch (IOException ex) {
            return Status.LOI_KETNOI_SERVER;
        }
    }
    
    public int suaDiemIQ(String username,String IQ){
        try {
            BUS.writeLine(Key.SUA_IQ);
            BUS.writeLine(username);
            BUS.writeLine(IQ);
            BUS.flush();
            System.out.println("Gui diem IQ :" +IQ);
            System.out.println("Gui user : " +username);
            return Status.OK;
        } catch (IOException ex) {
            return Status.LOI_KETNOI_SERVER;
        }
    }

    public ArrayList<NguoiDungDTO> search(String username) {
        ArrayList<NguoiDungDTO> find = new ArrayList<NguoiDungDTO>();
        NguoiDungDTO ndFind;
        for (NguoiDungDTO nd : BUS.userTmp) {
            if (nd.getTenNguoiDung().toLowerCase().contains(username.trim().toLowerCase())) {
                ndFind = new NguoiDungDTO(nd);
                find.add(ndFind);
            }
        }
        return find;
    }
}
