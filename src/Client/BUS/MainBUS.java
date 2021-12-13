package Client.BUS;

import Client.Status;
import Shares.DTO.NguoiDungDTO;
import Shares.Key;
import java.io.IOException;
import java.util.ArrayList;

public class MainBUS {

    public MainBUS() {
    }

    public int playgame() {
        try {
            BUS.writeLine(Key.PLAY_GAME);
            BUS.flush();
            return Status.OK;
        } catch (IOException ex) {
            return Status.LOI_KETNOI_SERVER;
        }
    }

    public int canclegame() {
        try {
            BUS.writeLine(Key.CANCLE_GAME);
            BUS.flush();
            return Status.OK;
        } catch (IOException ex) {
            return Status.LOI_KETNOI_SERVER;
        }
    }
    
    public ArrayList<NguoiDungDTO> search(String username){
        ArrayList<NguoiDungDTO> find = new ArrayList<NguoiDungDTO>(); 
        NguoiDungDTO ndFind;
        for(NguoiDungDTO nd: BUS.userTmp)
        {
        	if( nd.getTenNguoiDung().contains(username.trim().toLowerCase()))
        	{
        		ndFind = new NguoiDungDTO(nd);
                        find.add(ndFind);      
                }          
        }   
        return find;
    }
}
