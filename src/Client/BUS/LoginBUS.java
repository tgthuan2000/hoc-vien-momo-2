package Client.BUS;

import Client.Status;
import Shares.Key;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginBUS {

    public LoginBUS() {
    }
    

    public int login(String usr, String pwd) {
        try {
            pwd = BUS.getMd5(pwd); // mã hoá mật khẩu
            // kết nối server
            BUS.connect();
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
