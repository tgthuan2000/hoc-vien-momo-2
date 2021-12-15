package Server.Terminal.DTO;

import Shares.DTO.NguoiDungDTO;

public class Room {

    private String roomID;
    private NguoiDungDTO user1;
    private NguoiDungDTO user2;
    private int user1Accept;
    private int user2Accept;

    public final int UNKNOW = 0;
    public final int ACCEPT = 1;
    public final int DENY = -1;

    public Room() {
        user1Accept = UNKNOW;
        user2Accept = UNKNOW;
    }

    public Room(NguoiDungDTO user1, NguoiDungDTO user2, String roomId) {
        this.user1 = user1;
        this.user2 = user2;
        this.roomID = roomId;
        user1Accept = UNKNOW;
        user2Accept = UNKNOW;
    }

    public void setRoomID(String room) {
        roomID = room;
    }

    public String getRoomID() {
        return roomID;
    }

    public NguoiDungDTO getUser1() {
        return user1;
    }

    public void setUser1(NguoiDungDTO user1) {
        this.user1 = user1;
    }

    public NguoiDungDTO getUser2() {
        return user2;
    }

    public void setUser2(NguoiDungDTO user2) {
        this.user2 = user2;
    }

    public int getUser1Accept() {
        return user1Accept;
    }

    public void setUser1Accept(int user1Accept) {
        this.user1Accept = user1Accept;
    }

    public int getUser2Accept() {
        return user2Accept;
    }

    public void setUser2Accept(int user2Accept) {
        this.user2Accept = user2Accept;
    }

}
