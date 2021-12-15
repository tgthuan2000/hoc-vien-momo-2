package Server.Terminal.Thread;

import Server.BUS.MaHoaBUS;
import Server.BUS.ServerBUS;
import Server.BUS.UserBUS;
import Server.Terminal.DTO.Game;
import Server.Terminal.DTO.Key_RSA_AES_Server;
import Server.Terminal.ServerMain;
import Shares.DTO.CauHoiDTO;
import Shares.DTO.NguoiDungDTO;
import Shares.Key;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameWorker implements Runnable {

    public final Socket socket;
    public final BufferedReader in;
    public final BufferedWriter out;
    public final String roomId;
    public final NguoiDungDTO player1;
    public final NguoiDungDTO player2;
    public ArrayList<CauHoiDTO> cauHois;
    public int STT;
    public String dapAn;
    public GameWorker user2;
    public final Key_RSA_AES_Server AES;
    public int score;
    public int totalScore;
    public long timeStart;
    public long timeEnd;
    public long time;
    private boolean life;

    public GameWorker(Socket s, Key_RSA_AES_Server aes, String roomId, NguoiDungDTO player1, NguoiDungDTO player2) throws IOException {
        this.AES = aes;
        this.roomId = roomId;
        this.player1 = player1;
        this.player2 = player2;
        cauHois = new ArrayList<>();
        STT = 1;
        dapAn = "";
        totalScore = 0;
        life = true;

        this.socket = s;
        this.in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        this.out = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
    }

    private void writeLine(int num) throws IOException {
        out.write(MaHoaBUS.encrypt(num + "", AES.secretKey) + "\n");
    }

    private void writeLine(long num) throws IOException {
        out.write(MaHoaBUS.encrypt(num + "", AES.secretKey) + "\n");
    }

    private void writeLine(boolean b) throws IOException {
        out.write(MaHoaBUS.encrypt(b + "", AES.secretKey) + "\n");
    }

    private void writeLine(String str) throws IOException {
        out.write(MaHoaBUS.encrypt(str.trim(), AES.secretKey) + "\n");
    }

    private String readLine() throws IOException {
        String temp = MaHoaBUS.decrypt(in.readLine(), AES.secretKey);
        return temp != null ? temp : Key.FAILD_TO_DECRYPT;
    }

    @Override
    public void run() {
        System.out.println("Client " + socket.toString() + " accepted room game");
        try {
            prepareGame();
            while (life) {
                try {
                    switch (readLine()) {
                        case Key.GUI_DAPAN:
                            guiDapAnUser2();
                            break;
                        case Key.NHAN_DAPAN_USER2:
                            checkDapAn2User();
                            break;
                        case Key.TONGDIEM_USER2:
                            guiTongDiemUser2();
                            break;
                        case Key.DAPAN_DUNG:
                            guiDapAnDung();
                            break;
                        case Key.TIMEOUT:
                            timeout();
                            break;
                    }
                } catch (IOException ex) {
                    break;
                } catch (InterruptedException ex) {
                    Logger.getLogger(GameWorker.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            synchronized (this) {
                notify(); // back home
            }
        } catch (IOException ex) {
            Logger.getLogger(GameWorker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void prepareGame() throws IOException {
        getUser2();
        getCauHinh();
        getCauHoi();
        guiInfoUser2();
        guiCauHoi();
        guiCauHinh();
        prepareOK();
        System.out.println("Khởi tạo user " + player1.getUsername() + " hoàn tất!");

    }

    private GameWorker getUser2() {
        for (GameWorker gw : ServerMain.gameWorkers) {
            if (gw.roomId.equals(roomId) && gw.player1.equals(player2)) {
                user2 = gw;
            }
        }
        return null;
    }

    private void getCauHinh() {
        ServerBUS bus = new ServerBUS();
        score = bus.readConfig().getDiemTranDau(); // điểm tối đa 1 câu đúng
        time = bus.readConfig().getThoiGian(); // thời gian mỗi câu

    }

    private void getCauHoi() {
        for (Game game : ServerMain.games) {
            if (game.getRoomId().equals(roomId)) {
                cauHois = game.getCauHois();
            }
        }
    }

    private void guiInfoUser2() throws IOException {
        writeLine(Key.CAUHINH_INFO_USER_2);
        writeLine(player2.getTenNguoiDung());
        out.flush();
    }

    private void guiCauHoi() throws IOException {
        System.out.println("STT câu " + STT);
        CauHoiDTO cauHoi = cauHois.get(STT - 1);
        writeLine(Key.CAUHINH_CAUHOI);
        writeLine(cauHoi.getCauHoi());
        for (String str : randomDapAn(cauHoi)) {
            writeLine(str);
        }
        out.flush();
        timeStart = System.currentTimeMillis(); // tính thời gian gửi câu hỏi
    }

    private void guiCauHinh() throws IOException {
        writeLine(Key.CAUHINH_THOIGIAN);
        writeLine(time);
        out.flush();

        writeLine(Key.CAUHINH_SOCAU);
        writeLine(cauHois.size());
        out.flush();
    }

    private ArrayList<String> randomDapAn(CauHoiDTO cauHoi) {
        ArrayList<String> arrStr = new ArrayList<>();
        arrStr.add(cauHoi.getCauDung());
        arrStr.add(cauHoi.getCauSai1());
        arrStr.add(cauHoi.getCauSai2());
        arrStr.add(cauHoi.getCauSai3());
        Collections.shuffle(arrStr);
        return arrStr;
    }

    private void prepareOK() throws IOException {
        writeLine(Key.PREPARE_GAME_OK);
        out.flush();
    }

    private void guiDiemCau() throws IOException {
        int diem = 0;
        if (dapAn.equals(cauHois.get(STT - 1).getCauDung())) {
            long second = (timeEnd - timeStart) / 1000;
            diem = (int) (score * (time - second) / time);
            totalScore += diem;
            System.out.println("Điểm câu " + STT + " của " + player1.getUsername() + " là: " + diem);
        } else {
            System.out.println("Đáp án của " + player1.getUsername() + " sai => 0đ");
        }

        writeLine(Key.DIEM_CAU);
        writeLine(diem);
        out.flush();

        writeLine(Key.CAPNHAT_TONGDIEM);
        writeLine(totalScore);
        out.flush();

        timeStart = 0;
        timeEnd = 0;
    }

    private void guiDapAnUser2() throws IOException {
        dapAn = readLine();
        timeEnd = System.currentTimeMillis(); // tính thời gian gửi kq
        System.out.println("Đáp án user " + player1.getUsername() + ": " + dapAn);
        user2.writeLine(Key.DAPAN_USER2);
        user2.writeLine(dapAn);
        user2.out.flush();
    }

    private void checkDapAn2User() throws IOException {
        if (!dapAn.isEmpty()) {
            user2.writeLine(Key.MO_2_DAPAN);
            user2.out.flush();
            writeLine(Key.MO_2_DAPAN);
            out.flush();
        }
    }

    private void guiDapAnDung() throws IOException {
        writeLine(Key.DAPAN_DUNG);
        writeLine(cauHois.get(STT - 1).getCauDung());
        out.flush();
        guiDiemCau(); // gửi điểm trước khi guiCauHoi vì dapAn và STT sẽ bị thay đổi
    }

    private void nextCauHoi() throws InterruptedException, IOException {
        if (cauHois.size() == STT) {
            writeLine(Key.FINISHED_GAME);
            String status;
            if (totalScore > user2.totalScore) {
                status = Key.WINER;
            } else if (totalScore < user2.totalScore) {
                status = Key.LOSER;
            } else {
                status = Key.DRAW;
            }
            writeLine(status);
            out.flush();

            new UserBUS().capNhatDiem(player1, totalScore, status);

            life = false;
        } else {
            STT++;
            dapAn = "";
            TimeUnit.MILLISECONDS.sleep(4000);
            guiCauHoi();
            writeLine(Key.NEXT_CAU);
            out.flush();
        }
    }

    private void guiTongDiemUser2() throws IOException, InterruptedException {
        writeLine(Key.TONGDIEM_USER2);
        writeLine(user2.totalScore);
        out.flush();
        nextCauHoi();
    }

    private void timeout() throws IOException {
        dapAn = "";
        timeEnd = System.currentTimeMillis(); // tính thời gian gửi kq
        System.out.println("Đáp án user " + player1.getUsername() + ": " + dapAn);
        user2.writeLine(Key.DAPAN_USER2);
        user2.writeLine(dapAn);
        user2.out.flush();
    }
}
