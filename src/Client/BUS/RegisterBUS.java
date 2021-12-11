package Client.BUS;

import Client.Status;
import Shares.DTO.NguoiDungDTO;
import Shares.Key;
import Shares.ServerConfig;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class RegisterBUS {

    private NguoiDungDTO nguoiDung;

    // Constructor dùng khi trong Gui Register
    public RegisterBUS() {
    }

    // Constructor dùng khi trong Gui OTP
    public RegisterBUS(NguoiDungDTO nguoiDung) {
        this.nguoiDung = nguoiDung;
    }

    public int dangKy(String st) {
        try {
            // kết nối server
            BUS.socket = new Socket(ServerConfig.SERVER, ServerConfig.PORT);
            BUS.out = new BufferedWriter(new OutputStreamWriter(BUS.getOutputStream()));
            BUS.in = new BufferedReader(new InputStreamReader(BUS.getInputStream()));
            System.out.println("Client connected");

            // gửi tín hiệu đăng kí và kiểm tra email
            BUS.writeLine(Key.DANGKY);
            BUS.writeLine(st);
            BUS.flush();

            // kiểm tra nhận đăng kí để chuyển sang otp
            String rs = BUS.readLine();
            if (rs.equals(Key.NHAN_DANGKY)) {
                return Status.OK;
            } else if (rs.equals(Key.KONHAN_GUI_OTP)) {
                return Status.LOI_GUI_OTP;
            }
            return Status.FAILD; // KONHAN_DANGKY
        } catch (IOException ex) {
        }
        return Status.LOI_KETNOI_SERVER;
    }

    public int guiLaiOtp() {
        try {
            // gửi tín hiệu gửi lại otp và mail
            BUS.writeLine(Key.GUILAI_OTP);
            BUS.writeLine(nguoiDung.getUsername());
            BUS.flush();
            // kiểm tra nhận kết quả otp
            if (BUS.readLine().equals(Key.NHAN_GUILAI_OTP)) {
                return Status.OK;
            }
            return Status.FAILD;
        } catch (IOException ex) {
            return Status.LOI_KETNOI_SERVER;
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
                    return Status.OK;
                }
                return Status.LOI_GHI_CSDL; // lỗi ghi cơ sở dữ liệu
            }
            return Status.FAILD;
        } catch (IOException ex) {
            return Status.LOI_KETNOI_SERVER;
        }
    }
}
