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

public class RegisterBUS {

    public ArrayList<NguoiDungDTO> nguoiDungs;

    private final String host = ServerConfig.SERVER;
    private final int port = ServerConfig.PORT;
    private NguoiDungDTO nguoiDung;

    public RegisterBUS() {
    }

    public RegisterBUS(NguoiDungDTO nguoiDung) {
        this.nguoiDung = nguoiDung;
    }

    public int handleClientToServer(String st) {
        try {
            BUS.socket = new Socket(host, port);

            System.out.println("Client connected");
            BUS.out = new BufferedWriter(new OutputStreamWriter(BUS.getOutputStream()));
            BUS.in = new BufferedReader(new InputStreamReader(BUS.getInputStream()));

            BUS.writeLine(Key.DANGKY);
            BUS.writeLine(st);
            BUS.flush();
            return BUS.readLine().equals(Key.NHAN_DANGKY) ? 1 : 0;
        } catch (IOException ex) {
            return -1;
        }
    }

    public int guiLaiOtp() {
        try {
            BUS.writeLine(Key.GUILAI_OTP);
            BUS.writeLine(nguoiDung.getUsername());
            BUS.flush();
            return BUS.readLine().equals(Key.NHAN_GUILAI_OTP) ? 1 : 0;
        } catch (IOException ex) {
            return -1;
        }
    }

    public int checkOtp(String otp) {
        try {
            BUS.writeLine(Key.CHECK_OTP);
            BUS.writeLine(otp);
            BUS.flush();
            if (BUS.readLine().equals(Key.NHAN_KETQUA_CHECK_OTP)) {
                // Gửi lại server toàn bộ thông tin user đăng kí để ghi vào cơ sở dữ liệu
                BUS.writeLine(Key.GUI_THONGTIN_USER);
                BUS.writeLine(nguoiDung.getPassword());
                BUS.writeLine(nguoiDung.getTenNguoiDung());
                BUS.writeLine(nguoiDung.getNgaySinh());
                BUS.writeLine(nguoiDung.getGioiTinh() + "");
                BUS.flush();
                if (BUS.readLine().equals(Key.NHAN_KETQUA_DANGKY)) {
                    BUS.user = nguoiDung; // login
                    return 1;
                }
                return -2;
            }
            return 0;
        } catch (IOException ex) {
            return -1;
        }
    }
}
