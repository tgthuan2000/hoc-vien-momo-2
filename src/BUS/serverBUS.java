/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BUS;

import DTO.CauHinhDTO;
import DTO.CauHinhDTO;
import DTO.CauHoiDTO;
import DTO.CauHoiDTO;
import Server.ServerHocVienMomo.DAO.serverDAO;
import java.util.ArrayList;

/**
 *
 * @author NGAN DOAN
 */
public class serverBUS {
     public ArrayList<CauHinhDTO> conf;
    public ArrayList<CauHoiDTO> dsch;
    
    public ArrayList<CauHinhDTO> readConfig(){
        serverDAO data=new serverDAO();
            conf=data.readCauhinh();
       
        return conf;
    }
    
     public boolean changeconfig(CauHinhDTO con){
        if(new serverDAO().change(con))
            return true;
        return false;
    }
     
     public ArrayList<CauHoiDTO> readcauhoi(){
        serverDAO data=new serverDAO();
            dsch=data.readCauhoi();
       
        return dsch;
    }
     public boolean addcauhoi(CauHoiDTO cauhoi){
        if(new serverDAO().add(cauhoi))
            return readcauhoi().add(cauhoi);
        return false;
    }
}
