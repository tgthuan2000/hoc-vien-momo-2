package Server.Terminal.Thread;

import Server.Terminal.DTO.Game;
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
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameWorker implements Runnable {

    private final Socket socket;
    private final BufferedReader in;
    private final BufferedWriter out;
    private final String roomId;
    private final NguoiDungDTO player1;
    private final NguoiDungDTO player2;
    private ArrayList<CauHoiDTO> cauHois;
    private int STT;
    private String dapAn;
    private GameWorker user2;

    public GameWorker(Socket s, String roomId, NguoiDungDTO player1, NguoiDungDTO player2) throws IOException {
        this.socket = s;
        this.in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        this.out = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
        this.roomId = roomId;
        this.player1 = player1;
        this.player2 = player2;
        cauHois = new ArrayList<>();
        STT = 1;
        dapAn = "";
    }

    private void writeLine(String str) throws IOException {
        out.write(str.trim() + "\n");
    }

    private void writeLine(int num) throws IOException {
        out.write(num + "\n");
    }

    private void writeLine(boolean b) throws IOException {
        out.write(b + "\n");
    }

    private String readLine() throws IOException {
        return in.readLine();
    }

    @Override
    public void run() {
        System.out.println("Client " + socket.toString() + " accepted room game");
        try {
            prepareGame();
            while (true) {
                try {
                    switch (readLine()) {
                        case Key.GUI_DAPAN:
                            guiDapAnUser2();
                            break;
                        case Key.NHAN_DAPAN_USER2:
                            checkDapAn2User();
                            break;
                        case Key.DAPAN_DUNG:
                            guiDapAnDung();
                            break;
                        case Key.CAU_HOI:
                            nextCauHoi();
                            break;
                    }
                } catch (IOException ex) {
                    break;
                }
            }
            ServerMain.gui.useronl();
            in.close();
            out.close();
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(GameWorker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void prepareGame() throws IOException {
        getUser2();
        getCauHoi();
        guiInfoUser2();
        guiCauHoi();
        prepareOK();
        System.out.println("Danh sách câu hỏi");

        for (int i = 0; i < cauHois.size(); i++) {
            System.out.println("Câu hỏi số " + (i + 1) + " :" + cauHois.get(i).getCauHoi());
        }

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

    private void getCauHoi() {
        for (Game game : ServerMain.games) {
            if (game.getRoomId().equals(roomId)) {
                cauHois = game.getCauHois();
            }
        }
    }

    private void guiInfoUser2() throws IOException {
        writeLine(Key.INFO_USER_2);
        writeLine(player2.getTenNguoiDung());
        out.flush();
    }

    private void nextCauHoi() throws IOException {
        guiCauHoi();
        writeLine(Key.NEXT_CAU);
        out.flush();
    }

    private void guiCauHoi() throws IOException {
        System.out.println("STT câu " + STT);
        CauHoiDTO cauHoi = cauHois.get(STT - 1);
        writeLine(Key.CAU_HOI);
        writeLine(cauHoi.getCauHoi());
        for (String str : randomDapAn(cauHoi)) {
            writeLine(str);
        }
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

    private void guiDapAnUser2() throws IOException {
        dapAn = readLine();
        System.out.println("Đáp án user " + player1.getUsername() + ": " + dapAn);
        user2.writeLine(Key.DAPAN_USER2);
        user2.writeLine(dapAn);
        user2.out.flush();
    }

    private void guiDapAnDung() throws IOException {
        writeLine(Key.DAPAN_DUNG);
        writeLine(cauHois.get(STT - 1).getCauDung());
        STT++;
        dapAn = "";
        out.flush();
    }

    private void checkDapAn2User() throws IOException {
        if (!dapAn.isEmpty()) {
            user2.writeLine(Key.MO_2_DAPAN);
            user2.out.flush();
            writeLine(Key.MO_2_DAPAN);
            out.flush();
        }
    }
}
