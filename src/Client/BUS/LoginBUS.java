package Client.BUS;

import Client.Status;
import Shares.Key;
import java.io.IOException;

public class LoginBUS {

    public LoginBUS() {
    }

    public int login(String usr, String pwd) {
        try {
            pwd = BUS.getMd5(pwd); // mã hoá mật khẩu
            // gửi tín hiệu đăng nhập và usr, pwd
            BUS.writeLine(Key.DANGNHAP);
            BUS.writeLine(usr);
            BUS.writeLine(pwd);
            BUS.flush();
            return Status.OK;
        } catch (IOException ex) {
            return Status.LOI_KETNOI_SERVER;
        }
    }
}
