package Server.Terminal;

import Server.BUS.UserBUS;
import Shares.DTO.NguoiDungDTO;
import Shares.Key;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Properties;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private final int from = 89999;
    private final int to = 10000;
    private final String Subject = "OTP from 3 anh em with love";
    private final Socket socket;
    private final BufferedReader in;
    private final BufferedWriter out;
    private final UserBUS userBUS;
    private String email = null;
    private String roomId = null;
    private int Otp;
    private NguoiDungDTO nguoiDungDTO;

    public Worker(Socket s) throws IOException {
        this.socket = s;
        this.in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        this.out = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
        userBUS = new UserBUS();
        nguoiDungDTO = new NguoiDungDTO();
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

    private String readLine(String hash) {
        // giải mã
        return "";
    }

    @Override
    public void run() {
        System.out.println("Client " + socket.toString() + " accepted");
        try {
            while (true) {
                try {
                    switch (in.readLine()) {    // cú pháp phân biệt lệnh
                        case Key.DANGKY:
                            dangKy();
                            break;
                        case Key.DANGNHAP:
                            dangNhap();
                            break;
                        case Key.GUILAI_OTP:
                            guiLaiOtp();
                            break;
                        case Key.CHECK_OTP:
                            checkOtp();
                            break;
                        case Key.GUI_THONGTIN_USER:
                            luuNguoiDung();
                            break;
                        case Key.PLAY_GAME:
                            playgame();
                            break;
                        case Key.CANCLE_GAME:
                            canclegame();
                            break;
                        case Key.CHECK_ACCEPT_GAME:
                            checkAcceptGame();
                            break;
                    }
                } catch (IOException ex) {
                    break;
                }
            }
            ServerMain.users.remove(nguoiDungDTO);
            ServerMain.users_watting.remove(nguoiDungDTO);
            in.close();
            out.close();
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(Worker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void dangNhap() {
        try {
            String username = in.readLine();
            String password = in.readLine();
            nguoiDungDTO = userBUS.login(username, password);

            if (nguoiDungDTO.getUsername() != null) {
                if (nguoiDungDTO.isIsBlock()) {
                    writeLine(Key.TAIKHOAN_BLOCK);
                    out.flush();
                    System.out.println("Tài khoản đã bị block");
                } else {
                    boolean flag = true;
                    // kiểm tra user có online chưa
                    for (NguoiDungDTO user : ServerMain.users) {
                        if (user.getUsername().equals(nguoiDungDTO.getUsername())) {
                            flag = false;
                            break;
                        }
                    }

                    if (flag && ServerMain.users.add(nguoiDungDTO)) { // user online
                        infoUser();
                    } else {
                        writeLine(Key.DATONTAI_DANGNHAP);
                        System.out.println("Tài khoản đã đăng nhập");
                        out.flush();
                    }
                }
            } else {
                writeLine(Key.FAILD);
                System.out.println("Đăng nhập thất bại");
                out.flush();
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private void infoUser() throws IOException {
        writeLine(Key.NHAN_DANGNHAP);
        writeLine(nguoiDungDTO.getTenNguoiDung());
        writeLine(nguoiDungDTO.getChuoiThang());
        writeLine(nguoiDungDTO.getChuoiThangMax());
        writeLine(nguoiDungDTO.getChuoiThua());
        writeLine(nguoiDungDTO.getChuoiThuaMax());
        writeLine(nguoiDungDTO.getDiemIQ());
        writeLine(nguoiDungDTO.getGioiTinh());
        writeLine(nguoiDungDTO.getNgaySinh());
        writeLine(nguoiDungDTO.getTongDiem());
        writeLine(nguoiDungDTO.getTongTran());
        writeLine(nguoiDungDTO.getTongTranThang());
        out.flush();
    }

    private void dangKy() {
        try {
            StringBuilder str = new StringBuilder();
            str.append(in.readLine()); // email

            if (userBUS.kiemTra(str.toString())) {
                email = str.toString();
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
            System.out.println("Nhận OTP");
            if (in.readLine().equals(Integer.toString(Otp))) {
                System.out.println("OTP đúng");
                writeLine(Key.NHAN_KETQUA_CHECK_OTP);
            } else {
                System.out.println("OTP sai");
                writeLine(Key.FAILD);
            }
            out.flush();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private void luuNguoiDung() {
        try {
            nguoiDungDTO.setUsername(email);
            nguoiDungDTO.setPassword(in.readLine());
            nguoiDungDTO.setTenNguoiDung(in.readLine());
            nguoiDungDTO.setNgaySinh(in.readLine());
            nguoiDungDTO.setGioiTinh(in.readLine().equals("true"));

            System.out.println("Nhận người dùng");
            if (userBUS.ThemNguoiDung(nguoiDungDTO)) {
                writeLine(Key.OK);
                ServerMain.users.add(nguoiDungDTO);
                System.out.println("Lưu người dùng " + email + " thành công");
            } else {
                writeLine(Key.LOI_GHI_CSDL);
                System.out.println("Lưu người dùng " + email + " thất bại");
            }
            out.flush();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private void playgame() {
        try {
            if (ServerMain.users_watting.add(nguoiDungDTO)) {
                System.out.println("Cho người dùng " + nguoiDungDTO.getUsername() + " vào phòng chờ");
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
        int size = ServerMain.users_watting.size();

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
        String usr1 = ServerMain.users_watting.get(user1).getUsername();
        String usr2 = ServerMain.users_watting.get(user2).getUsername();

        Room waittingRoom = new Room();
        String id = "123";
        for (Worker worker : ServerMain.workers) {
            String usr = worker.nguoiDungDTO.getUsername();
            if (usr.equals(usr1)) {
                System.out.println("Gửi accept user 1");
                sendAccept(worker, id);
                waittingRoom.setUser1(worker.nguoiDungDTO);
            }
            if (usr.equals(usr2)) {
                System.out.println("Gửi accept user 2");
                sendAccept(worker, id);
                waittingRoom.setUser2(worker.nguoiDungDTO);
            }
        }
        waittingRoom.setRoomID(id);
        ServerMain.waittingRooms.add(waittingRoom);
    }

    private void sendAccept(Worker worker, String id) throws IOException {
        worker.writeLine(Key.ACCEPT_GAME);
        worker.writeLine(id); // room id
        worker.out.flush();
        ServerMain.users_watting.remove(worker.nguoiDungDTO);
    }

    private void canclegame() {
        try {
            if (ServerMain.users_watting.remove(nguoiDungDTO)) {
                System.out.println("Người dùng " + nguoiDungDTO.getUsername() + " đã thoát phòng chờ");
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
        switch (in.readLine()) {
            case Key.OK:
                ok_accept_game();
                break;
            case Key.FAILD:
                deny_game();
                break;
        }
    }

    private void ok_accept_game() throws IOException {
        roomId = in.readLine();
        for (Room r : ServerMain.waittingRooms) {
            if (r.getRoomID().equals(roomId)) {
                if (r.getUser1().equals(nguoiDungDTO)) {
                    r.setUser1Accept(r.ACCEPT);
                    System.out.println("user 1 accept");
                } else {
                    r.setUser2Accept(r.ACCEPT);
                    System.out.println("user 2 accept");
                }

                if (r.getUser1Accept() == r.DENY || r.getUser2Accept() == r.DENY) {
                    if (ServerMain.waittingRooms.remove(r) && ServerMain.users_watting.add(nguoiDungDTO)) {
                        System.out.println("Add " + nguoiDungDTO.getTenNguoiDung());
                        writeLine(Key.NO_CONTINUE_GAME);
                        out.flush();
                        roomId = null;
                        // có thể sleep -> cho dui
                        ghepcap();
                    }
                }

                // cuối
                if (r.getUser1Accept() == r.ACCEPT && r.getUser2Accept() == r.ACCEPT) {
                    System.out.println("2 user accept");
                    //ServerMain.executorRoom.execute(new RoomWorker(socket, r));
                }
                break;
            }
        }
    }

    private void deny_game() throws IOException {
        roomId = in.readLine();
        for (Room r : ServerMain.waittingRooms) {
            if (r.getRoomID().equals(roomId)) {
                if (r.getUser1().equals(nguoiDungDTO)) {
                    r.setUser1Accept(r.DENY);
                    System.out.println("user 1 deny");
                } else {
                    r.setUser2Accept(r.DENY);
                    System.out.println("user 2 deny");
                }

                if (r.getUser1Accept() == r.ACCEPT) {
                    for (Worker worker : ServerMain.workers) {
                        if (worker.nguoiDungDTO.equals(r.getUser1())) {
                            worker.writeLine(Key.NO_CONTINUE_GAME);
                            worker.out.flush();
                            worker.roomId = null;
                            // có thể sleep -> cho dui
                            ghepcap();
                            break;
                        }
                    }
                }

                if (r.getUser2Accept() == r.ACCEPT) {
                    for (Worker worker : ServerMain.workers) {
                        if (worker.nguoiDungDTO.equals(r.getUser2())) {
                            worker.writeLine(Key.NO_CONTINUE_GAME);
                            worker.out.flush();
                            worker.roomId = null;
                            // có thể sleep -> cho dui
                            ghepcap();
                            break;
                        }
                    }
                }

                break;
            }
        }
        roomId = null;
        System.out.println("Người dùng " + nguoiDungDTO.getUsername() + " đã thoát phòng chờ");
        writeLine(Key.CANCLE_GAME);
        out.flush();
    }
}
