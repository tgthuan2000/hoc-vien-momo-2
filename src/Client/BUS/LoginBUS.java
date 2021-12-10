package Client.BUS;

import Shares.DTO.NguoiDungDTO;
import Shares.Key;
import Shares.ServerConfig;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

public class LoginBUS {

    public ArrayList<NguoiDungDTO> nguoiDungs;
    private final String host = ServerConfig.SERVER;
    private final int port = ServerConfig.PORT;
    private NguoiDungDTO nguoiDung;

    public LoginBUS() {
    }

    public int login(String usr, String pwd) {
        try {
            BUS.socket = new Socket(host, port);
            BUS.out = new BufferedWriter(new OutputStreamWriter(BUS.socket.getOutputStream()));
            BUS.in = new BufferedReader(new InputStreamReader(BUS.socket.getInputStream()));

            BUS.out.write(Key.DANGNHAP + "\n");
            BUS.out.write(usr + "\n");
            BUS.out.write(pwd + "\n");
            BUS.out.flush();
            if (BUS.in.readLine().equals(Key.NHAN_DANGNHAP)) {
                nguoiDung.setTenNguoiDung(BUS.in.readLine());
                nguoiDung.setChuoiThang(Integer.parseInt(BUS.in.readLine()));
                nguoiDung.setChuoiThangMax(Integer.parseInt(BUS.in.readLine()));
                nguoiDung.setChuoiThua(Integer.parseInt(BUS.in.readLine()));
                nguoiDung.setChuoiThuaMax(Integer.parseInt(BUS.in.readLine()));
                nguoiDung.setDiemIQ(Integer.parseInt(BUS.in.readLine()));
                nguoiDung.setGioiTinh(BUS.in.readLine().equals("true"));
                nguoiDung.setNgaySinh(BUS.in.readLine());
                nguoiDung.setTongDiem(Integer.parseInt(BUS.in.readLine()));
                nguoiDung.setTongTran(Integer.parseInt(BUS.in.readLine()));
                nguoiDung.setTongTranThang(Integer.parseInt(BUS.in.readLine()));

                return 1;
            }
            return 0;
        } catch (IOException ex) {
            return -1;
        }
    }
}
