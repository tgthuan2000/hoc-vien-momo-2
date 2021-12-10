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
            BUS.out = new BufferedWriter(new OutputStreamWriter(BUS.socket.getOutputStream()));
            BUS.in = new BufferedReader(new InputStreamReader(BUS.socket.getInputStream()));

            BUS.out.write(Key.DANGKY + "\n");
            BUS.out.write(st.trim() + "\n");
            BUS.out.flush();
            return BUS.in.readLine().equals(Key.NHAN_DANGKY) ? 1 : 0;
        } catch (IOException ex) {
            return -1;
        }
    }

    public int guiLaiOtp() {
        try {
            BUS.out.write(Key.GUILAI_OTP + "\n");
            BUS.out.write(nguoiDung.getUsername() + "\n");
            BUS.out.flush();
            return BUS.in.readLine().equals(Key.NHAN_GUILAI_OTP) ? 1 : 0;
        } catch (IOException ex) {
            return -1;
        }
    }

    public int checkOtp(String otp) {
        try {
            BUS.out.write(Key.CHECK_OTP + "\n");
            BUS.out.write(otp + "\n");
            BUS.out.flush();
            if (BUS.in.readLine().equals(Key.NHAN_KETQUA_CHECK_OTP)) {
                BUS.out.write(Key.GUI_THONGTIN_USER + "\n");
                BUS.out.write(nguoiDung.getPassword() + "\n");
                BUS.out.write(nguoiDung.getTenNguoiDung() + "\n");
                BUS.out.write(nguoiDung.getNgaySinh() + "\n");
                BUS.out.write(nguoiDung.getGioiTinh() + "\n");
                BUS.out.flush();
                return BUS.in.readLine().equals(Key.NHAN_KETQUA_DANGKY) ? 1 : -2;
            }
            return 0;
        } catch (IOException ex) {
            return -1;
        }
    }
}
