package Client;

import Client.BUS.BUS;
import Client.BUS.RSA_AESBUS;
import Client.GUI.Form;
import Client.GUI.Main;
import Client.GUI.PlayGame;
import Shares.DTO.NguoiDungDTO;
import Shares.Key;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;
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

    private void writeLine(int num) throws IOException {
        out.write(RSA_AESBUS.encrypt(num + "", KeyRSA_AES.keyAES) + "\n");
    }

    private void writeLine(String str) throws IOException {
        out.write(RSA_AESBUS.encrypt(str.trim(), KeyRSA_AES.keyAES) + "\n");
    }

    private String readLine() throws IOException {
        String temp = RSA_AESBUS.decrypt(in.readLine(), KeyRSA_AES.keyAES);
        return temp != null ? temp : Key.FAILD_TO_DECRYPT;
    }

    private int readLineInt() throws IOException {
        return Integer.parseInt(readLine());
    }

    @Override
    public void run() {
        try {
            while (true) {
                try {
                    switch (readLine()) {    // cú pháp phân biệt lệnh
                        case Key.FAILD_TO_DECRYPT:
                            System.out.println("Faild to decrypt");
                            faild();
                            break;
                        case Key.FAILD:
                            faild();
                            break;
                        case Key.OK:
                            ok();
                            break;

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
                        case Key.CANCLE_GAME:
                            cancleGame();
                            break;
                        case Key.NO_CONTINUE_GAME:
                            no_continue_game();
                            break;
                        case Key.LOAD_GAME:
                            loadgame();
                            break;

                        //
                        // IN GAME
                        //
                        case Key.CAUHINH_INFO_USER_2:
                            getInfoUser2();
                            break;
                        case Key.CAUHINH_CAUHOI:
                            getCauHoi();
                            break;
                        case Key.CAUHINH_THOIGIAN:
                            getThoiGian();
                            break;
                        case Key.CAUHINH_SOCAU:
                            getSoCauHoi();
                            break;
                        case Key.PREPARE_GAME_OK:
                            prepare_ok();
                            break;
                        case Key.DAPAN_USER2:
                            nhanDapAnUser2();
                            break;
                        case Key.DAPAN_DUNG:
                            dapAnDung();
                            break;
                        case Key.DIEM_CAU:
                            readScore();
                            break;
                        case Key.CAPNHAT_TONGDIEM:
                            readTotalScore();
                            break;
                        case Key.TONGDIEM_USER2:
                            setTotalScoreUser2();
                            break;
                        case Key.MO_2_DAPAN:
                            moDapAnUser2();
                            break;
                        case Key.TIMEOUT:
                            timeout();
                            break;
                        case Key.NEXT_CAU:
                            nextCau();
                            break;
                        case Key.FINISHED_GAME:
                            finishGame();
                            break;
                        case Key.LOAD_INFO_AFTERGAME:
                            reloadMain();

                        case Key.SUCCESS_SUA_IQ:
                            ok();
                            break;

                        case Key.USER2_DISCONECTED:
                            sendUserDisconected();
                            break;
                    }
                } catch (IOException ex) {
                    break;
                } catch (InterruptedException ex) {
                    Logger.getLogger(WorkerClient.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(WorkerClient.class.getName()).log(Level.SEVERE, null, ex);
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
            nguoiDung.setUsername(readLine());
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
        } catch (IOException ex) {
            errConn();
        }
    }

    private void nhanBangXepHang() throws IOException {
        ArrayList<String> tmp = new ArrayList<>();
        StringTokenizer token = new StringTokenizer(readLine(), "$$"); // nội dung tin

        while (token.hasMoreTokens()) {
            String a = token.nextToken();
            tmp.add(a);
        }
        if (tmp.size() == 6) {
            BUS.users.add(new NguoiDungDTO(tmp.get(0), Integer.parseInt(tmp.get(1)), Integer.parseInt(tmp.get(2)), Integer.parseInt(tmp.get(3)), Integer.parseInt(tmp.get(4)), Integer.parseInt(tmp.get(5))));
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
            System.out.println("Có game");
            String roomId = readLine();
            System.out.println("Room id: " + roomId);
            // user có thể bị ghép loại khỏi ghép cặp tức thì do user 2 không chấp nhận game
            if (JOptionPane.showConfirmDialog(null, "Chấp nhận vào game!!!") == JOptionPane.YES_OPTION) {
                Main.flag = true;
                writeLine(Key.CHECK_ACCEPT_GAME);
                writeLine(Key.OK);
                writeLine(roomId); // room id
                out.flush();
                System.out.println("Chấp nhận game");

                // block ng dùng
                Main.btnPlay.setEnabled(false);
                Main.btnIQ.setEnabled(false);

            } else {
                writeLine(Key.CHECK_ACCEPT_GAME);
                writeLine(Key.FAILD);
                writeLine(roomId); // room id
                out.flush();
                System.out.println("Từ chối game");
            }
        } catch (IOException ex) {
        }
    }

    private void cancleGame() throws IOException {
        Main.flag = true;
        Main.btnPlay.setText("Bắt đầu");
    }

    private void no_continue_game() {
        JOptionPane.showMessageDialog(null, "Trận đấu bị huỷ do đối phương không chấp nhận trận đấu!!!");
        Main.btnPlay.setEnabled(true);
        Main.btnIQ.setEnabled(true);
    }

    private void loadgame() throws IOException {
        writeLine(Key.LOAD_GAME);
        out.flush();
        Form.hideMain();
        Form.hideInterfaceIQ();

    }

    private void getInfoUser2() throws IOException {
        BUS.user2 = new NguoiDungDTO();
        BUS.user2.setTenNguoiDung(readLine());
    }

    private void getCauHoi() throws IOException {
        BUS.cauHoi = readLine();
        BUS.dsDapAn = new ArrayList<>();
        BUS.dsDapAn.add(readLine());
        BUS.dsDapAn.add(readLine());
        BUS.dsDapAn.add(readLine());
        BUS.dsDapAn.add(readLine());
    }

    private void getThoiGian() throws IOException {
        BUS.thoiGian = readLineInt();
    }

    private void getSoCauHoi() throws IOException {
        BUS.soCau = readLineInt();
    }

    private void prepare_ok() throws Exception {
        Form.newPlayGame();
    }

    private void nhanDapAnUser2() throws IOException {
        BUS.dapAnUser2 = readLine();
        writeLine(Key.NHAN_DAPAN_USER2);
        out.flush();
    }

    private void dapAnDung() throws IOException, InterruptedException {
        getDapAn(readLine(), Color.GREEN);
    }

    private void getDapAn(String dapAn, Color color) throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(3000);
        for (int i = 0; i < BUS.dsDapAn.size(); i++) {
            if (BUS.dsDapAn.get(i).equals(dapAn)) {
                switch (i) {
                    case 0:
                        PlayGame.btnA.setBackground(color);
                        break;
                    case 1:
                        PlayGame.btnB.setBackground(color);
                        break;
                    case 2:
                        PlayGame.btnC.setBackground(color);
                        break;
                    case 3:
                        PlayGame.btnD.setBackground(color);
                        break;
                }
            }
        }
    }

    private void moDapAnUser2() throws InterruptedException, IOException {
        if (PlayGame.dapAn.equals(BUS.dapAnUser2)) {
            getDapAn(BUS.dapAnUser2, Color.ORANGE);
        } else {
            getDapAn(BUS.dapAnUser2, Color.BLUE);
        }
        writeLine(Key.DAPAN_DUNG);
        out.flush();
    }

    private void nextCau() {
        PlayGame.getCau();
        PlayGame.refresh();
    }

    private void readScore() throws IOException {
        BUS.diem = readLineInt();
        PlayGame.showScore();
    }

    private void readTotalScore() throws IOException {
        BUS.tongDiem = readLineInt();
        PlayGame.setTotalScore();
        writeLine(Key.TONGDIEM_USER2);
        writeLine(BUS.tongDiem);
        out.flush();
    }

    private void setTotalScoreUser2() throws IOException {
        BUS.tongDiemUser2 = readLineInt();
        PlayGame.setTotalScoreUser2();
    }

    private void finishGame() throws IOException, InterruptedException, Exception {
        TimeUnit.MILLISECONDS.sleep(2000);
        String rs = "";
        switch (readLine()) {
            case Key.WINER:
                rs = "Chúc mừng người chiến thắng!!!";
                break;
            case Key.LOSER:
                rs = "Thua gòi làm lại game mới đi :(";
                break;
            case Key.DRAW:
                rs = "Cân tài cân sức ha :D";
                break;
        }
        JOptionPane.showMessageDialog(null, "Kết thúc game\n" + rs);
        System.out.println("Kết thức game!");
        BUS.users.clear();
        BUS.user = new NguoiDungDTO();
    }

    private void reloadMain() throws Exception {
        Form.hidePlayGame();
        Form.newMain();
    }

    private void sendUserDisconected() throws Exception {
        System.out.println("Mất kết nối với đối thủ");
        writeLine(Key.REFRESH_FOR_DISCONNECT);
        out.flush();
        JOptionPane.showMessageDialog(null, "Đã mất kết nối với đối thủ!!!");
        Form.hidePlayGame();
        Form.newMain();
    }

    private void timeout() throws IOException {
        writeLine(Key.DAPAN_DUNG);
        out.flush();
    }

}
