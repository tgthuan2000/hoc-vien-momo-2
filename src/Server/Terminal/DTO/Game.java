package Server.Terminal.DTO;

import Shares.DTO.CauHoiDTO;
import java.util.ArrayList;

public class Game {

    private String roomId;
    private ArrayList<CauHoiDTO> cauHois;

    public Game(String roomId, ArrayList<CauHoiDTO> cauHois) {
        this.roomId = roomId;
        this.cauHois = cauHois;
    }

    public Game() {
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public ArrayList<CauHoiDTO> getCauHois() {
        return cauHois;
    }

    public void setCauHois(ArrayList<CauHoiDTO> cauHois) {
        this.cauHois = cauHois;
    }

}
