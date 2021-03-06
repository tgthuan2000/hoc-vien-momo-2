package Server.Terminal;

import Server.BUS.MaHoaBUS;
import Server.BUS.ServerBUS;
import Server.BUS.UserBUS;
import Server.Terminal.DTO.Game;
import Server.Terminal.DTO.Key_RSA_AES_Server;
import Server.Terminal.DTO.Room;
import static Server.Terminal.ServerMain.gui;
import Shares.DTO.CauHoiDTO;
import Shares.DTO.NguoiDungDTO;
import Shares.Key;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Worker implements Runnable {

    private final String userMail = "tgthuan23012000@gmail.com";
    private final String pwdMail = "TGThuan12A4";
//    private final String userMail = "hoducthangtn2nhvt@gmail.com";
//    private final String pwdMail = "16112000@Abc";
//    private final String userMail = "ngandoan110500@gmail.com";
//    private final String pwdMail = "ngan@123";
    private final int from = 89999;
    private final int to = 10000;
    private final String Subject = "OTP from 3 anh em with love";
    private final Socket socket;
    private final BufferedReader in;
    private final BufferedWriter out;
    private final UserBUS userBUS;
    private String email = null;
    private int Otp;
    private NguoiDungDTO user;
    private String roomId = null;

    public static String privateKey;
    public final Key_RSA_AES_Server AES;
    public NguoiDungDTO player2;
    public ArrayList<CauHoiDTO> cauHois;
    public int STT;
    public String dapAn;
    public Worker workerUser2;
    public int score;
    public int totalScore;
    public long timeStart;
    public long timeEnd;
    public long time;

    public Worker(Socket s) throws IOException, Exception {
        this.socket = s;
        this.in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        this.out = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));

        userBUS = new UserBUS();

        user = new NguoiDungDTO();
        player2 = new NguoiDungDTO();
        AES = new Key_RSA_AES_Server();
        refresh();
        sendPublicKey(); // read and send public key RSA to client
        AES.secretKey = decryptMessage(in.readLine(), AES.privateKey); // decode public key RSA => get AES key
        System.out.println("AES key of client: " + AES.secretKey);
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
        System.out.println("Client " + socket.toString() + " accepted");
        try {
            while (true) {
                try {
                    switch (readLine()) {    // c?? ph??p ph??n bi???t l???nh
                        // L???i gi???i m??
                        case Key.FAILD_TO_DECRYPT:
                            System.out.println(user.getUsername() + " faild to decrypt!!!");
                            writeLine(Key.FAILD_TO_DECRYPT);
                            out.flush();
                            break;

                        //
                        //SUA IQ
                        //
                        case Key.SUA_IQ:
                            suaIQ();
                            break;
                        //

                        // SIGNIN
                        //
                        case Key.DANGKY:
                            dangKy();
                            break;
                        case Key.GUILAI_OTP:
                            guiLaiOtp();
                            break;
                        case Key.CHECK_OTP:
                            checkOtp();
                            break;

                        //
                        // LOGIN
                        //
                        case Key.DANGNHAP:
                            dangNhap();
                            break;
                        case Key.GUI_THONGTIN_USER:
                            luuNguoiDung();
                            break;

                        //
                        // EVENT GAMES
                        //
                        case Key.PLAY_GAME:
                            playgame();
                            break;
                        case Key.CANCLE_GAME:
                            canclegame();
                            break;
                        case Key.CHECK_ACCEPT_GAME:
                            checkAcceptGame();
                            break;
                        case Key.LOAD_GAME:
                            prepareGame();
                            break;

                        //
                        // INGAME
                        //
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
                        case Key.REFRESH_FOR_DISCONNECT:
                            refreshDisconected();
                            break;

                    }
                } catch (IOException ex) {
                    break;
                } catch (InterruptedException ex) {
                } catch (Exception ex) {
                    Logger.getLogger(Worker.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            disconected();
            // clean up
            cleanup();
            System.out.println("user " + user.getUsername() + " disconected");
            System.out.println("User online: " + ServerMain.users.size());
            in.close();
            out.close();
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(Worker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void cleanup() throws IOException {
        cancleGameWhenUserClose();
        ServerMain.users_waitting.remove(user);
        ServerMain.users.remove(user);
        ServerMain.workers.remove(this);
        ServerMain.gui.useronl();
    }

    private void disconected() throws IOException {
        if (workerUser2 != null) { // in game
            System.out.println("ingame");
            workerUser2.writeLine(Key.USER2_DISCONECTED);
            workerUser2.out.flush();
            ServerMain.gameWorkers.remove(this);
        }
    }

    private void refreshDisconected() throws IOException {
        refresh();
        ServerMain.gameWorkers.remove(this);
    }

    //
    // LOGIN
    //
    private void dangNhap() {
        try {
            String username = readLine();
            String password = readLine();

            user = userBUS.login(username, password);

            if (user.getUsername() != null) {
                if (user.isIsBlock()) {
                    writeLine(Key.TAIKHOAN_BLOCK);
                    out.flush();
                    System.out.println("T??i kho???n ???? b??? block");
                } else {
                    boolean flag = true;
                    // ki???m tra user c?? online ch??a
                    for (NguoiDungDTO usr : ServerMain.users) {
                        if (usr.getUsername().equals(user.getUsername())) {
                            flag = false;
                            break;
                        }
                    }

                    if (flag && ServerMain.users.add(user)) { // user online
                        gui.useronl();
                        // load data
                        infoUser();
                        inforRank();
                        writeLine(Key.OK);
                        out.flush();
                    } else {
                        writeLine(Key.DATONTAI_DANGNHAP);
                        System.out.println("T??i kho???n ???? ????ng nh???p");
                        out.flush();
                    }
                }
            } else {
                writeLine(Key.FAILD);
                System.out.println("????ng nh???p th???t b???i");
                out.flush();
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private void infoUser() throws IOException {
        writeLine(Key.NHAN_DANGNHAP);
        writeLine(user.getUsername());
        writeLine(user.getTenNguoiDung());
        writeLine(user.getChuoiThang());
        writeLine(user.getChuoiThangMax());
        writeLine(user.getChuoiThua());
        writeLine(user.getChuoiThuaMax());
        writeLine(user.getDiemIQ());
        writeLine(user.getGioiTinh());
        writeLine(user.getNgaySinh());
        writeLine(user.getTongDiem());
        writeLine(user.getTongTran());
        writeLine(user.getTongTranThang());
        out.flush();
    }

    public void inforRank() throws IOException {
        for (NguoiDungDTO nd : userBUS.docdulieu()) {
            writeLine(Key.DANHSACH_BXH);
            StringBuilder builder = new StringBuilder();
            builder.append(nd.getTenNguoiDung() + "$$"
                    + nd.getChuoiThangMax() + "$$"
                    + nd.getChuoiThuaMax() + "$$"
                    + nd.getTongTran() + "$$"
                    + nd.getTongTranThang() + "$$"
                    + nd.getTongDiem());
            writeLine(builder.toString());
            out.flush();
        }
    }

    //
    // SIGNIN
    //
    private void dangKy() {
        try {
            StringBuilder str = new StringBuilder();
            str.append(readLine()); // email

            if (userBUS.kiemTra(str.toString())) {
                email = str.toString();
                System.out.println(email);
                if (guiOtp()) {
                    writeLine(Key.OK);
                } else {
                    writeLine(Key.KONHAN_GUI_OTP);
                }
            } else {
                writeLine(Key.FAILD);
            }
            out.flush();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private void guiLaiOtp() {
        try {
            if (guiOtp()) {
                writeLine(Key.OK);
            } else {
                writeLine(Key.FAILD);
            }
            out.flush();
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    private boolean guiOtp() {
        Random random = new Random();
        Otp = random.nextInt(from) + to;

        System.out.println("Sent: " + String.valueOf(Otp));

        // send mail
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userMail, pwdMail);
            }
        });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(userMail));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(email)
            );
            message.setSubject(Subject);
            message.setText(String.valueOf(Otp));

            Transport.send(message);

            System.out.println("Done");
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void checkOtp() {
        try {
            System.out.println("Nh???n OTP");
            if (readLine().equals(Integer.toString(Otp))) {
                System.out.println("OTP ????ng");
                writeLine(Key.NHAN_KETQUA_CHECK_OTP);

                inforRank();
            } else {
                System.out.println("OTP sai");
                writeLine(Key.FAILD);
            }
            out.flush();
            System.out.println("done");
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private void luuNguoiDung() {
        try {
            user.setUsername(email);
            user.setPassword(readLine());
            user.setTenNguoiDung(readLine());
            user.setNgaySinh(readLine());
            user.setGioiTinh(readLine().equals("true"));

            System.out.println("Nh???n ng?????i d??ng");
            if (userBUS.ThemNguoiDung(user)) {
                ServerMain.users.add(user);
                gui.useronl();
                gui.users();
                writeLine(Key.OK);
                System.out.println("L??u ng?????i d??ng " + email + " th??nh c??ng");

            } else {
                writeLine(Key.LOI_GHI_CSDL);
                System.out.println("L??u ng?????i d??ng " + email + " th???t b???i");
            }
            out.flush();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    //
    // EVENT PLAY GAME
    //
    private void playgame() {
        try {
            if (ServerMain.users_waitting.add(user)) {
                System.out.println("Cho ng?????i d??ng " + user.getUsername() + " v??o ph??ng ch???");
                writeLine(Key.OK);
                out.flush();
                ghepcap();
            } else {
                writeLine(Key.FAILD);
                out.flush();
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private void ghepcap() throws IOException {
        int size = ServerMain.users_waitting.size();

        if (size == 2) {
            sendAccept(0, 1);
        } else if (size > 2) {
            Random random = new Random();
            int user1;
            int user2;
            do {
                user1 = random.nextInt(size);
                user2 = random.nextInt(size);
            } while (user1 == user2);

            sendAccept(user1, user2);
        }
    }

    private void sendAccept(int user1, int user2) throws IOException {
        NguoiDungDTO usr1 = ServerMain.users_waitting.get(user1);
        NguoiDungDTO usr2 = ServerMain.users_waitting.get(user2);

        Room waittingRoom = new Room();
        String id = "room-" + (new Random().nextInt(89999999) + 10000000);
        waittingRoom.setRoomID(id);
        System.out.println("Room id: " + id);
        for (Worker worker : ServerMain.workers) {
            NguoiDungDTO usr = worker.user;
            if (usr.equals(usr1)) {
                System.out.println("G???i accept user " + usr1.getUsername());
                sendAccept(worker, id);
                waittingRoom.setUser1(worker.user);
            }
            if (usr.equals(usr2)) {
                System.out.println("G???i accept user " + usr2.getUsername());
                sendAccept(worker, id);
                waittingRoom.setUser2(worker.user);
            }
        }
        ServerMain.waittingRooms.add(waittingRoom);
    }

    private void sendAccept(Worker worker, String roomId) throws IOException {
        worker.writeLine(Key.ACCEPT_GAME);
        worker.writeLine(roomId);
        worker.out.flush();
        ServerMain.users_waitting.remove(worker.user);
    }

    private void canclegame() {
        try {
            if (ServerMain.users_waitting.remove(user)) {
                System.out.println("Ng?????i d??ng " + user.getUsername() + " ???? tho??t ph??ng ch???");
                writeLine(Key.OK);
            } else {
                writeLine(Key.FAILD);
            }
            out.flush();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private void checkAcceptGame() throws IOException {
        switch (readLine()) {
            case Key.OK:
                ok_accept_game();
                break;
            case Key.FAILD:
                deny_game();
                break;
        }
    }

    private void ok_accept_game() throws IOException {
        roomId = readLine();
        // ki???m tra user n??o click accept b???ng roomid
        for (Room r : ServerMain.waittingRooms) {
            if (r.getRoomID().equals(roomId)) {
                int currentUser = 0;
                if (r.getUser1().equals(user)) {
                    currentUser = 1;
                    r.setUser1Accept(r.ACCEPT);
                    player2 = r.getUser2();
                    System.out.println("user " + user.getUsername() + " accept");
                } else {
                    r.setUser2Accept(r.ACCEPT);
                    System.out.println("user " + user.getUsername() + " accept");
                    player2 = r.getUser1();
                }

                // khi user n??y accept m?? c?? 1 user deny tr?????c ????
                if (r.getUser1Accept() == r.DENY || r.getUser2Accept() == r.DENY) {
                    if (ServerMain.waittingRooms.remove(r) && ServerMain.users_waitting.add(user)) {
                        System.out.println("Hu??? room: " + r.getRoomID());
                        System.out.println("Cho ng?????i d??ng " + user.getTenNguoiDung() + " v??o l???i h??ng ch???");
                        writeLine(Key.NO_CONTINUE_GAME);
                        out.flush();
                        roomId = null;
                        player2 = new NguoiDungDTO();
                        ghepcap();
                    }
                }

                // cu???i
                if (r.getUser1Accept() == r.ACCEPT && r.getUser2Accept() == r.ACCEPT && ServerMain.games.add(new Game(r.getRoomID(), randomCauHoi()))) {
                    System.out.println("2 user accept");
                    if (currentUser == 1) {
                        for (Worker worker : ServerMain.workers) {
                            if (worker.user != null && worker.user.equals(r.getUser2())) {
                                loadgame(worker);
                                break;
                            }
                        }
                    } else {
                        for (Worker worker : ServerMain.workers) {
                            if (worker.user.equals(r.getUser1())) {
                                loadgame(worker);
                                break;
                            }
                        }
                    }
                    ServerMain.waittingRooms.remove(r);
                }
                break;
            }
        }
    }

    private void send_no_continue(Worker worker) throws IOException {
        ServerMain.users_waitting.add(worker.user);
        System.out.println("Add user " + worker.user.getUsername() + " v??o h??ng ch???");
        worker.writeLine(Key.NO_CONTINUE_GAME);
        worker.out.flush();
        worker.roomId = null;
    }

    private void cancleGameWhenUserClose() throws IOException {
        for (Room r : ServerMain.waittingRooms) {
            if (r.getUser1().equals(user)) {
                r.setUser1Accept(r.DENY);
                System.out.println("user " + user.getUsername() + " disconnected game");
                break;
            } else if (r.getUser2().equals(user)) {
                r.setUser2Accept(r.DENY);
                System.out.println("user " + user.getUsername() + " disconnected game");
                break;
            }
        }
    }

    private void loadgame(Worker worker) throws IOException {
        writeLine(Key.LOAD_GAME);
        out.flush();
        worker.writeLine(Key.LOAD_GAME);
        worker.out.flush();
        ServerMain.gameWorkers.add(this);
        ServerMain.gameWorkers.add(worker);
    }

    private ArrayList<CauHoiDTO> randomCauHoi() {
        ServerBUS bus = new ServerBUS();
        ArrayList<CauHoiDTO> cauHois = new ArrayList<>();

        ArrayList<Integer> ins = new ArrayList<>();
        int iNew;
        Random rd = new Random();
        int sl = bus.readConfig().getSoLuongCauHoi();
        ArrayList<CauHoiDTO> dsCauHoi = bus.readcauhoi();

        System.out.println("S??? l?????ng c??u h???i c???u h??nh: " + sl);
        System.out.println("S??? l?????ng c??u h???i hi???n c??: " + dsCauHoi.size());

        for (int i = 1; i <= sl; i++) {
            while (true) {
                iNew = rd.nextInt(dsCauHoi.size());
                if (!ins.contains(iNew)) {
                    ins.add(iNew);
                    cauHois.add(dsCauHoi.get(iNew));
                    break;
                }
            }
        }
        return cauHois;
    }

    private void deny_game() throws IOException {
        roomId = readLine();
        // ki???m tra user n??o click deny b???ng roomid
        for (Room r : ServerMain.waittingRooms) {
            if (r.getRoomID().equals(roomId)) {
                if (r.getUser1().equals(user)) {
                    r.setUser1Accept(r.DENY);
                    System.out.println("user " + user.getUsername() + " deny");

                    // ki???m tra user tr?????c ???? nh???n accept
                    if (r.getUser2Accept() == r.ACCEPT) {
                        for (Worker worker : ServerMain.workers) {
                            if (worker.user.equals(r.getUser2())) {
                                System.out.println("Hu??? room: " + r.getRoomID());
                                ServerMain.waittingRooms.remove(r);
                                send_no_continue(worker);
                                ghepcap();
                                break;
                            }
                        }
                    }
                } else {
                    r.setUser2Accept(r.DENY);
                    System.out.println("user " + user.getUsername() + " deny");

                    // ki???m tra user tr?????c ???? nh???n accept
                    if (r.getUser1Accept() == r.ACCEPT) {
                        for (Worker worker : ServerMain.workers) {
                            if (worker.user.equals(r.getUser1())) {
                                System.out.println("Hu??? room: " + r.getRoomID());
                                ServerMain.waittingRooms.remove(r);
                                send_no_continue(worker);
                                ghepcap();
                                break;
                            }
                        }
                    }
                }

                System.out.println("Ng?????i d??ng " + user.getUsername() + " ???? tho??t ph??ng ch???");
                roomId = null;
                writeLine(Key.CANCLE_GAME);
                out.flush();

                if (r.getUser1Accept() == r.DENY && r.getUser2Accept() == r.DENY) {
                    ServerMain.waittingRooms.remove(r);
                    System.out.println("Hu??? room: " + r.getRoomID());
                }
                break;
            }
        }
    }

    //
    // INGAME
    //
    private void prepareGame() throws IOException, InterruptedException {
        getUser2();
        getCauHinh();
        getCauHoi();
        guiInfoUser2();
        guiCauHoi();
        guiCauHinh();
        prepareOK();
        System.out.println("Kh???i t???o user " + user.getUsername() + " ho??n t???t!");
    }

    private Worker getUser2() {
        for (Worker wk : ServerMain.gameWorkers) {
            if (wk.roomId.equals(roomId) && wk.user.equals(player2)) {
                workerUser2 = wk;
            }
        }
        return null;
    }

    private void getCauHinh() {
        ServerBUS bus = new ServerBUS();
        score = bus.readConfig().getDiemTranDau(); // ??i???m t???i ??a 1 c??u ????ng
        time = bus.readConfig().getThoiGian(); // th???i gian m???i c??u

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
        System.out.println("STT c??u " + STT);
        CauHoiDTO cauHoi = cauHois.get(STT - 1);
        writeLine(Key.CAUHINH_CAUHOI);
        writeLine(cauHoi.getCauHoi());
        for (String str : randomDapAn(cauHoi)) {
            writeLine(str);
        }
        out.flush();
        timeStart = System.currentTimeMillis(); // t??nh th???i gian g???i c??u h???i
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
            System.out.println("??i???m c??u " + STT + " c???a " + user.getUsername() + " l??: " + diem);
        } else {
            System.out.println("????p ??n c???a " + user.getUsername() + " sai => 0??");
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
        timeEnd = System.currentTimeMillis(); // t??nh th???i gian g???i kq
        System.out.println("????p ??n user " + user.getUsername() + ": " + dapAn);
        workerUser2.writeLine(Key.DAPAN_USER2);
        workerUser2.writeLine(dapAn);
        workerUser2.out.flush();
    }

    private void checkDapAn2User() throws IOException {
        if (!dapAn.isEmpty()) {
            workerUser2.writeLine(Key.MO_2_DAPAN);
            workerUser2.out.flush();
            writeLine(Key.MO_2_DAPAN);
            out.flush();
        }
    }

    private void guiDapAnDung() throws IOException {
        writeLine(Key.DAPAN_DUNG);
        writeLine(cauHois.get(STT - 1).getCauDung());
        out.flush();
        guiDiemCau(); // g???i ??i???m tr?????c khi guiCauHoi v?? dapAn v?? STT s??? b??? thay ?????i
    }

    private void nextCauHoi() throws InterruptedException, IOException {
        if (cauHois.size() == STT) {
            writeLine(Key.FINISHED_GAME);
            String status;
            if (totalScore > workerUser2.totalScore) {
                status = Key.WINER;
            } else if (totalScore < workerUser2.totalScore) {
                status = Key.LOSER;
            } else {
                status = Key.DRAW;
            }

            userBUS.capNhatDiem(user, totalScore, status);
            user = userBUS.getNewInfo(user);

            writeLine(status);
            out.flush();
            infoUser();
            inforRank();
            writeLine(Key.LOAD_INFO_AFTERGAME);
            out.flush();
            refresh();
            ServerMain.gameWorkers.remove(this);
        } else {
            STT++;
            dapAn = "";
            TimeUnit.MILLISECONDS.sleep(4000);
            guiCauHoi();
            writeLine(Key.NEXT_CAU);
            out.flush();
        }
    }

    private void refresh() {
        STT = 1;
        dapAn = "";
        workerUser2 = null;
        score = 0;
        totalScore = 0;
        timeStart = 0;
        timeEnd = 0;
        time = 0;
        player2 = new NguoiDungDTO();
    }

    private void guiTongDiemUser2() throws IOException, InterruptedException {
        writeLine(Key.TONGDIEM_USER2);
        writeLine(workerUser2.totalScore);
        out.flush();
        nextCauHoi();
    }

    //
    // CRYPT
    //
    public void sendPublicKey() throws Exception {
        Map<String, Object> keys = getRSAKeys();
        PublicKey publicKey = (PublicKey) keys.get("public"); // public key RSA
        AES.privateKey = (PrivateKey) keys.get("private"); // private key RSA
        //System.out.println("Server send PLK RSA: " + castPublicKeyToString(publicKey));
        out.write(castPublicKeyToString(publicKey) + "\n"); // send public RSA to client
        out.flush();
    }

    private String castPublicKeyToString(PublicKey publicKey) {
        // Base64 encoded string
        Base64.Encoder encoder = Base64.getEncoder();
        String publicKeyStr = encoder.encodeToString(publicKey.getEncoded());
        return publicKeyStr;
    }

    //sinh khoa
    private static Map<String, Object> getRSAKeys() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        PrivateKey private_Key = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();

        Map<String, Object> keys = new HashMap<String, Object>();
        keys.put("private", private_Key);
        keys.put("public", publicKey);
        return keys;
    }

    // Decrypt using RSA public key
    private static String decryptMessage(String encryptedText, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return new String(cipher.doFinal(Base64.getDecoder().decode(encryptedText)));
    }

    private void suaIQ() throws IOException {
        String username = readLine();
        String Iq = readLine();
        if (userBUS.SuaIQ(username, Iq)) {
            System.out.println("Sua IQ thanh cong");
            writeLine(Key.SUCCESS_SUA_IQ);
        } else {

            System.out.println("Sua IQ that bai");
        }
        out.flush();
    }

}
