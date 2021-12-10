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
            pwd = BUS.getMd5(pwd);
            BUS.socket = new Socket(host, port);
            BUS.out = new BufferedWriter(new OutputStreamWriter(BUS.getOutputStream()));
            BUS.in = new BufferedReader(new InputStreamReader(BUS.getInputStream()));

            BUS.writeLine(Key.DANGNHAP);
            BUS.writeLine(usr);
            BUS.writeLine(pwd);
            BUS.flush();

            if (BUS.readLine().equals(Key.NHAN_DANGNHAP)) {
                nguoiDung.setTenNguoiDung(BUS.readLine());
                nguoiDung.setChuoiThang(BUS.readLineInt());
                nguoiDung.setChuoiThangMax(BUS.readLineInt());
                nguoiDung.setChuoiThua(BUS.readLineInt());
                nguoiDung.setChuoiThuaMax(BUS.readLineInt());
                nguoiDung.setDiemIQ(BUS.readLineInt());
                nguoiDung.setGioiTinh(BUS.readLine().equals("true"));
                nguoiDung.setNgaySinh(BUS.readLine());
                nguoiDung.setTongDiem(BUS.readLineInt());
                nguoiDung.setTongTran(BUS.readLineInt());
                nguoiDung.setTongTranThang(BUS.readLineInt());

                return 1;
            }
            return 0;
        } catch (IOException ex) {
            return -1;
        }
    }
}
