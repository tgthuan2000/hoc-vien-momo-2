package Client;

import Client.BUS.BUS;
import Shares.DTO.NguoiDungDTO;
import Shares.Key;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class WorkerClient implements Runnable {

    private final Socket socket;
    private final BufferedReader in;
    private final BufferedWriter out;

    public static int status;
    public static boolean isContinue;

    public WorkerClient(Socket s) throws IOException {
        this.socket = s;
        this.in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        this.out = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
        isContinue = false;
    }

    private void writeLine(String str) throws IOException {
        out.write(str.trim() + "\n");
    }

    public String readLine() throws IOException {
        return in.readLine();
    }

    public int readLineInt() throws IOException {
        return Integer.parseInt(in.readLine());
    }

    @Override
    public void run() {
        try {
            while (true) {
                try {
                    switch (in.readLine()) {    // cú pháp phân biệt lệnh
                        case Key.FAILD:
                            faild();
                            break;
                        case Key.OK:
                            ok();
                            
                        //
                        // ĐĂNG NHẬP
                        //
                        case Key.NHAN_DANGNHAP:
                            nhanDangNhap();
                            break;
                        case Key.DANHSACH_BXH:
                            nhanBangXepHang();
                        case Key.DATONTAI_DANGNHAP:
                            tonTaiDangNhap();
                            break;
                        case Key.TAIKHOAN_BLOCK:
                            taiKhoanBlock();
                            break;

                        //
                        // ĐĂNG KÝ
                        //
                        case Key.KONHAN_GUI_OTP:
                            koNhanOTP();
                            break;
                        case Key.NHAN_KETQUA_CHECK_OTP:
                            System.out.println("DZO diiii");
                            nhanKetQuaOTP();
                            break;
                        case Key.LOI_GHI_CSDL:
                            loiGhiCSDL();
                            break;

                        //
                        //  PLAY GAME
                        //
                        case Key.ACCEPT_GAME:
                            acceptGame();
                            break;
                    }
                } catch (IOException ex) {
                    break;
                }
            }
            in.close();
            out.close();
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(WorkerClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void faild() {
        status = Status.FAILD;
        isContinue = true;
    }

    private void ok() {
        System.out.println("KEY OK");
        status = Status.OK;
        isContinue = true;
    }

    private void errConn() {
        status = Status.LOI_KETNOI_SERVER;
        isContinue = true;
    }

    private void tonTaiDangNhap() {
        status = Status.LOI_TONTAI_DANGNHAP;
        isContinue = true;
    }

    private void taiKhoanBlock() {
        status = Status.LOI_BLOCK_TAIKHOAN;
        isContinue = true;
    }

    private void koNhanOTP() {
        status = Status.LOI_GUI_OTP;
        isContinue = true;
    }

    private void loiGhiCSDL() {
        status = Status.LOI_GHI_CSDL;
        isContinue = true;
    }

    private void nhanDangNhap() {
        try {
            NguoiDungDTO nguoiDung = new NguoiDungDTO();
            nguoiDung.setTenNguoiDung(readLine());
            nguoiDung.setChuoiThang(readLineInt());
            nguoiDung.setChuoiThangMax(readLineInt());
            nguoiDung.setChuoiThua(readLineInt());
            nguoiDung.setChuoiThuaMax(readLineInt());
            nguoiDung.setDiemIQ(readLineInt());
            nguoiDung.setGioiTinh(readLine().equals("true"));
            nguoiDung.setNgaySinh(readLine());
            nguoiDung.setTongDiem(readLineInt());
            nguoiDung.setTongTran(readLineInt());
            nguoiDung.setTongTranThang(readLineInt());

            BUS.user = nguoiDung; // đăng nhập, ghi nhận thông tin vào biến toàn cục
            ok();
        } catch (IOException ex) {
            errConn();
        }
    }
    
    private void nhanBangXepHang() throws IOException {
        BUS.users = new ArrayList<NguoiDungDTO>();
        ArrayList<String> tmp = new ArrayList<>(); ;
        while(true){
            StringTokenizer token = new StringTokenizer(readLine(),"$$");
            
            while(token.hasMoreTokens()){
                String a = token.nextToken();
                tmp.add(a);
            }
            if(tmp.size() == 6){
                BUS.users.add(new NguoiDungDTO(tmp.get(0),Integer.parseInt(tmp.get(1)), Integer.parseInt(tmp.get(2)), Integer.parseInt(tmp.get(3)), Integer.parseInt(tmp.get(4)),Integer.parseInt(tmp.get(5))));
            }
            System.out.println("Size : " +BUS.users.size());
            tmp = new ArrayList<>();
        }
    }
    private void nhanKetQuaOTP() {
        try {
            writeLine(Key.GUI_THONGTIN_USER);
            writeLine(BUS.user.getPassword());
            writeLine(BUS.user.getTenNguoiDung());
            writeLine(BUS.user.getNgaySinh());
            writeLine(BUS.user.getGioiTinh() + "");
            out.flush();
            ok();
        } catch (IOException ex) {
            errConn();
        }
    }

    private void acceptGame() {
        try {
            System.out.println("Chấp nhận game");
            if (JOptionPane.showConfirmDialog(null, "Chấp nhận vào game!!!") == JOptionPane.YES_OPTION) {
                writeLine(Key.OK_ACCEPT_GAME);
            } else {
                writeLine(Key.NO_ACCEPT_GAME);
            }
            out.flush();
        } catch (IOException ex) {
        }
    }

    

}
