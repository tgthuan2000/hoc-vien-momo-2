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
    private final String uuid;
    private final UserBUS userBUS;
    private String email;
    private int Otp;
    private NguoiDungDTO nguoiDungDTO;

    public Worker(Socket s, String uuid) throws IOException {
        this.socket = s;
        this.uuid = uuid;
        this.in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        this.out = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
        userBUS = new UserBUS();
        nguoiDungDTO = new NguoiDungDTO();
    }

    @Override
    public void run() {
        System.out.println("Client " + socket.toString() + " accepted");
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
                }
            } catch (IOException ex) {
                Logger.getLogger(Worker.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void dangNhap() {
        try {
            String username = in.readLine();
            String password = in.readLine();
            nguoiDungDTO = userBUS.login(username, password);
            if (!nguoiDungDTO.getUsername().isEmpty()) {
                out.write(Key.NHAN_DANGNHAP + "\n");
                out.write(nguoiDungDTO.getTenNguoiDung() + "\n");
                out.write(nguoiDungDTO.getChuoiThang() + "\n");
                out.write(nguoiDungDTO.getChuoiThangMax() + "\n");
                out.write(nguoiDungDTO.getChuoiThua() + "\n");
                out.write(nguoiDungDTO.getChuoiThuaMax() + "\n");
                out.write(nguoiDungDTO.getDiemIQ() + "\n");
                out.write(nguoiDungDTO.getGioiTinh() + "\n");
                out.write(nguoiDungDTO.getNgaySinh() + "\n");
                out.write(nguoiDungDTO.getTongDiem() + "\n");
                out.write(nguoiDungDTO.getTongTran() + "\n");
                out.write(nguoiDungDTO.getTongTranThang() + "\n");
            } else {
                out.write(Key.KONHAN_DANGNHAP + "\n");
            }
            out.flush();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private void dangKy() {
        try {
            StringBuilder str = new StringBuilder();
            str.append(in.readLine());

            if (userBUS.kiemTra(str.toString())) {
                email = str.toString();
                out.write(Key.NHAN_DANGKY + "\n");
                guiOtp();
            } else {
                out.write(Key.KONHAN_DANGKY + "\n");
            }
            out.flush();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private void guiLaiOtp() {
        try {
            guiOtp();
            out.write(Key.NHAN_GUILAI_OTP + "\n");
            out.flush();
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    private void guiOtp() {
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

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private void checkOtp() {
        try {
            System.out.println("Nhận OTP");
            if (in.readLine().equals(Integer.toString(Otp))) {
                System.out.println("OTP đúng");
                out.write(Key.NHAN_KETQUA_CHECK_OTP + "\n");
            } else {
                System.out.println("OTP sai");
                out.write(Key.KONHAN_KETQUA_CHECK_OTP + "\n");
            }
            out.flush();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private void luuNguoiDung() {
        try {
            NguoiDungDTO dto = new NguoiDungDTO();
            dto.setUsername(email);
            dto.setPassword(in.readLine());
            dto.setTenNguoiDung(in.readLine());
            dto.setNgaySinh(in.readLine());
            dto.setGioiTinh(in.readLine().equals("true"));

            System.out.println("Nhận người dùng");
            if (userBUS.ThemNguoiDung(dto)) {
                out.write(Key.NHAN_KETQUA_DANGKY + "\n");
                System.out.println("Lưu người dùng " + email + " thành công");
            } else {
                out.write(Key.KONHAN_KETQUA_DANGKY + "\n");
                System.out.println("Lưu người dùng " + email + " thất bại");
            }
            out.flush();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
