package Server.Terminal;

import Client.BUS.RSA_AESBUS;
import static Client.BUS.RSA_AESBUS.decrypt;
import Client.KeyRSA_AES;
import Server.BUS.MaHoaBUS;
//import static Server.BUS.MaHoaBUS.decrypt;
import Server.BUS.UserBUS;
import Server.Key_RSA_AES_Server;
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
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
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

    private final String userMail = "tgthuan2000@gmail.com";
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
    private Room room;
    public static String privateKey;

    public Worker(Socket s) throws IOException, Exception {
        this.socket = s;
        this.in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        this.out = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
        userBUS = new UserBUS();
        nguoiDungDTO = new NguoiDungDTO();
        room = new Room();
        sendPublicKey();
        Key_RSA_AES_Server.secretKey = decryptMessage(in.readLine(), Key_RSA_AES_Server.privateKey);
        System.out.println(Key_RSA_AES_Server.secretKey);
    }
    

    private void writeLine(int num) throws IOException {
        String tmp = MaHoaBUS.encrypt(num+"",Key_RSA_AES_Server.secretKey);
            out.write(tmp + "\n");
    }
    
    private void writeLine(boolean b) throws IOException {
        String tmp = MaHoaBUS.encrypt(b+"",Key_RSA_AES_Server.secretKey);
            out.write(tmp + "\n");
    }
    
    private void writeLine(String str) throws IOException {
            String tmp = MaHoaBUS.encrypt(str.trim(),Key_RSA_AES_Server.secretKey);
            out.write(tmp + "\n");
    }

    private String readLine() throws IOException {
            String readline = in.readLine();
            String tmp = MaHoaBUS.decrypt(readline, Key_RSA_AES_Server.secretKey);
            return tmp;
    }

    @Override
    public void run() {
        System.out.println("Client " + socket.toString() + " accepted");
        try {
            while (true) {
                try {
                    switch (readLine()) {  
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
                        case Key.LOAD_GAME:
                            goToGame();
                            break;
                    }
                } catch (IOException ex) {
                    break;
                } catch (Exception ex) {
                    Logger.getLogger(Worker.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            // clean up
            cleanup();
            System.out.println("user " + nguoiDungDTO.getUsername() + " disconected");
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
        ServerMain.users_waitting.remove(nguoiDungDTO);
        ServerMain.users.remove(nguoiDungDTO);
        ServerMain.workers.remove(this);
    }

    private void dangNhap() {
        try {
            String username = readLine();
            String password = readLine();
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
                        // load data
                        infoUser();
                        inforRank();
                        writeLine(Key.OK);
                        out.flush();
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
            System.out.println(builder.toString());
            writeLine(builder.toString());
            out.flush();
            System.out.println("done");
        }
    }

    private void dangKy() {
        try {
            StringBuilder str = new StringBuilder();
            str.append(readLine()); // email

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
            if (readLine().equals(Integer.toString(Otp))) {
                System.out.println("OTP đúng");
                writeLine(Key.NHAN_KETQUA_CHECK_OTP);
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
            nguoiDungDTO.setUsername(email);
            nguoiDungDTO.setPassword(readLine());
            nguoiDungDTO.setTenNguoiDung(readLine());
            nguoiDungDTO.setNgaySinh(readLine());
            nguoiDungDTO.setGioiTinh(readLine().equals("true"));

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
            if (ServerMain.users_waitting.add(nguoiDungDTO)) {
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
        System.out.println("Room id: " + id);
        for (Worker worker : ServerMain.workers) {
            NguoiDungDTO usr = worker.nguoiDungDTO;
            if (usr.equals(usr1)) {
                System.out.println("Gửi accept user " + usr1.getUsername());
                sendAccept(worker, id);
                waittingRoom.setUser1(worker.nguoiDungDTO);
            }
            if (usr.equals(usr2)) {
                System.out.println("Gửi accept user " + usr2.getUsername());
                sendAccept(worker, id);
                waittingRoom.setUser2(worker.nguoiDungDTO);
            }
        }
        waittingRoom.setRoomID(id);
        this.room = waittingRoom;
        ServerMain.waittingRooms.add(waittingRoom);
    }

    private void sendAccept(Worker worker, String id) throws IOException {
        worker.writeLine(Key.ACCEPT_GAME);
        worker.writeLine(id); // room id
        worker.out.flush();
        ServerMain.users_waitting.remove(worker.nguoiDungDTO);
    }

    private void canclegame() {
        try {
            if (ServerMain.users_waitting.remove(nguoiDungDTO)) {
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
        // kiểm tra user nào click accept bằng roomid
        for (Room r : ServerMain.waittingRooms) {
            if (r.getRoomID().equals(roomId)) {
                int currentUser = 0;
                if (r.getUser1().equals(nguoiDungDTO)) {
                    currentUser = 1;
                    r.setUser1Accept(r.ACCEPT);
                    System.out.println("user " + nguoiDungDTO.getUsername() + " accept");
                } else {
                    r.setUser2Accept(r.ACCEPT);
                    System.out.println("user " + nguoiDungDTO.getUsername() + " accept");
                }

                // khi user này accept mà có 1 user deny trước đó
                if (r.getUser1Accept() == r.DENY || r.getUser2Accept() == r.DENY) {
                    if (ServerMain.waittingRooms.remove(r) && ServerMain.users_waitting.add(nguoiDungDTO)) {
                        System.out.println("Huỷ room: " + r.getRoomID());
                        System.out.println("Cho người dùng " + nguoiDungDTO.getTenNguoiDung() + " vào lại hàng chờ");
                        writeLine(Key.NO_CONTINUE_GAME);
                        out.flush();
                        roomId = null;
                        ghepcap();
                    }
                }

                // cuối
                if (r.getUser1Accept() == r.ACCEPT && r.getUser2Accept() == r.ACCEPT) {
                    System.out.println("2 user accept");
                    if (currentUser == 1) {
                        for (Worker worker : ServerMain.workers) {
                            if (worker.nguoiDungDTO.equals(r.getUser2())) {
                                loadgame(worker);
                                break;
                            }
                        }
                    } else {
                        for (Worker worker : ServerMain.workers) {
                            if (worker.nguoiDungDTO.equals(r.getUser1())) {
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

    private void loadgame(Worker worker) throws IOException {
        writeLine(Key.LOAD_GAME);
        out.flush();
        worker.writeLine(Key.LOAD_GAME);
        worker.out.flush();
    }

    private void goToGame() throws IOException {
        RoomWorker rw = new RoomWorker(socket, room);
        ServerMain.executorRoom.execute(rw);
        ServerMain.roomWorkers.add(rw);

        in.close(); // đóng kết nối với worker user hiện tại
        out.close();
        socket.close();

        ServerMain.workers.remove(this);
    }

    private void deny_game() throws IOException {
        roomId = readLine();
        // kiểm tra user nào click deny bằng roomid
        for (Room r : ServerMain.waittingRooms) {
            if (r.getRoomID().equals(roomId)) {
                if (r.getUser1().equals(nguoiDungDTO)) {
                    r.setUser1Accept(r.DENY);
                    System.out.println("user " + nguoiDungDTO.getUsername() + " deny");

                    // kiểm tra user trước đó nhấn accept
                    if (r.getUser2Accept() == r.ACCEPT) {
                        for (Worker worker : ServerMain.workers) {
                            if (worker.nguoiDungDTO.equals(r.getUser2())) {
                                System.out.println("Huỷ room: " + r.getRoomID());
                                ServerMain.waittingRooms.remove(r);
                                send_no_continue(worker);
                                ghepcap();
                                break;
                            }
                        }
                    }
                } else {
                    r.setUser2Accept(r.DENY);
                    System.out.println("user " + nguoiDungDTO.getUsername() + " deny");

                    // kiểm tra user trước đó nhấn accept
                    if (r.getUser1Accept() == r.ACCEPT) {
                        for (Worker worker : ServerMain.workers) {
                            if (worker.nguoiDungDTO.equals(r.getUser1())) {
                                System.out.println("Huỷ room: " + r.getRoomID());
                                ServerMain.waittingRooms.remove(r);
                                send_no_continue(worker);
                                ghepcap();
                                break;
                            }
                        }
                    }
                }

                System.out.println("Người dùng " + nguoiDungDTO.getUsername() + " đã thoát phòng chờ");
                roomId = null;
                writeLine(Key.CANCLE_GAME);
                out.flush();

                if (r.getUser1Accept() == r.DENY && r.getUser2Accept() == r.DENY) {
                    ServerMain.waittingRooms.remove(r);
                    System.out.println("Huỷ room: " + r.getRoomID());
                }
                break;
            }
        }
    }

    private void send_no_continue(Worker worker) throws IOException {
        ServerMain.users_waitting.add(worker.nguoiDungDTO);
        System.out.println("Add user " + worker.nguoiDungDTO.getUsername() + " vào hàng chờ");
        worker.writeLine(Key.NO_CONTINUE_GAME);
        worker.out.flush();
        worker.roomId = null;
    }

    private void cancleGameWhenUserClose() throws IOException {
        for (Room r : ServerMain.waittingRooms) {
            if (r.getUser1().equals(nguoiDungDTO)) {
                r.setUser1Accept(r.DENY);
                System.out.println("user " + nguoiDungDTO.getUsername() + " disconnected game");
                break;
            } else if (r.getUser2().equals(nguoiDungDTO)) {
                r.setUser2Accept(r.DENY);
                System.out.println("user " + nguoiDungDTO.getUsername() + " disconnected game");
                break;
            }
        }
    }
    
    public void sendPublicKey() throws Exception{
        Map<String, Object> keys = getRSAKeys();
        PublicKey publicKey = (PublicKey) keys.get("public");
        Key_RSA_AES_Server.privateKey = (PrivateKey) keys.get("private");
        out.write(castPublicKeyToString(publicKey) + "\n");
        System.out.println("send publickey : " +castPublicKeyToString(publicKey));
        System.out.println("public key : " +publicKey.toString());
        out.flush();
        System.out.println("done");
    }
    
    private String castPublicKeyToString(PublicKey publicKey){
        // get encoded form (byte array)
        byte[] publicKeyByte = publicKey.getEncoded();
        // Base64 encoded string

        Base64.Encoder encoder = Base64.getEncoder();
        String publicKeyStr = encoder.encodeToString(publicKey.getEncoded());
        System.out.println("publicKeyString: " + publicKeyStr);
        return publicKeyStr;
    }
    
    //sinh khoa
    private static Map<String,Object> getRSAKeys() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();
 
        Map<String, Object> keys = new HashMap<String,Object>();
        keys.put("private", privateKey);
        keys.put("public", publicKey);
        return keys;
    }
   
    
     // Decrypt using RSA public key
    private static String decryptMessage(String encryptedText, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return new String(cipher.doFinal(Base64.getDecoder().decode(encryptedText)));
    }
}
