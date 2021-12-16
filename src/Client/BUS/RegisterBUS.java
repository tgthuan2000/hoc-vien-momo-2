package Client.BUS;

import Client.Status;
import Shares.DTO.NguoiDungDTO;
import Shares.Key;
import java.io.IOException;

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
//            BUS.connect();
            // gửi tín hiệu đăng kí và kiểm tra email
            BUS.writeLine(Key.DANGKY);
            BUS.writeLine(st); // email
            BUS.flush();
            return Status.OK;
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
            return Status.OK;
        } catch (IOException ex) {
            return Status.LOI_KETNOI_SERVER;
        }
    }

    public int checkOtp(String otp) {
        try {
            BUS.user = nguoiDung;
            BUS.writeLine(Key.CHECK_OTP);
            BUS.writeLine(otp);
            BUS.flush();
            System.out.println("Dzo check");
            return Status.OK;
        } catch (IOException ex) {
            return Status.LOI_KETNOI_SERVER;
        }
    }
}
