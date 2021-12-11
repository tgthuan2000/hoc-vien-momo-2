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

public class LoginBUS {

    public LoginBUS() {
    }

    public int login(String usr, String pwd) {
        try {
            pwd = BUS.getMd5(pwd); // mã hoá mật khẩu
            // kết nối server
            BUS.socket = new Socket(ServerConfig.SERVER, ServerConfig.PORT);
            BUS.out = new BufferedWriter(new OutputStreamWriter(BUS.getOutputStream()));
            BUS.in = new BufferedReader(new InputStreamReader(BUS.getInputStream()));

            // gửi tín hiệu đăng nhập và usr, pwd
            BUS.writeLine(Key.DANGNHAP);
            BUS.writeLine(usr);
            BUS.writeLine(pwd);
            BUS.flush();

            // nhận tín hiệu đăng nhập
            if (BUS.readLine().equals(Key.NHAN_DANGNHAP)) {
                NguoiDungDTO nguoiDung = new NguoiDungDTO();
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

                BUS.user = nguoiDung; // đăng nhập, ghi nhận thông tin vào biến toàn cục
                return Status.OK;
            }
            return Status.FAILD;
        } catch (IOException ex) {
            return Status.LOI_KETNOI_SERVER;
        }
    }
}
