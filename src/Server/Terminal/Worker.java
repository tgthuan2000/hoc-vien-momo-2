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
    private String email;
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
                    }
                } catch (IOException ex) {
                    break;
                }
            }
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
            boolean flag = true;
            if (nguoiDungDTO.getUsername() != null) {
                for (NguoiDungDTO user : ServerMain.users) {
                    if (user.getUsername().equals(nguoiDungDTO.getUsername())) {
                        flag = false;
                        break;
                    }
                }

                if (flag) {
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
                    ServerMain.users.add(nguoiDungDTO);
                } else {
                    writeLine(Key.DATONTAI_DANGNHAP);
                    System.out.println("Tài khoản đã đăng nhập");
                }
            } else {
                writeLine(Key.KONHAN_DANGNHAP);
                System.out.println("Đăng nhập thất bại");
            }
            out.flush();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private void dangKy() {
        try {
            StringBuilder str = new StringBuilder();
            str.append(in.readLine()); // email

            if (userBUS.kiemTra(str.toString())) {
                email = str.toString();
                if (guiOtp()) {
                    writeLine(Key.NHAN_DANGKY);
                } else {
                    writeLine(Key.KONHAN_GUI_OTP);
                }
            } else {
                writeLine(Key.KONHAN_DANGKY);
            }
            out.flush();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private void guiLaiOtp() {
        try {
            if (guiOtp()) {
                writeLine(Key.NHAN_GUILAI_OTP);
            } else {
                writeLine(Key.KONHAN_GUI_OTP);
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
                writeLine(Key.KONHAN_KETQUA_CHECK_OTP);
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
                writeLine(Key.NHAN_KETQUA_DANGKY);
                System.out.println("Lưu người dùng " + email + " thành công");
            } else {
                writeLine(Key.KONHAN_KETQUA_DANGKY);
                System.out.println("Lưu người dùng " + email + " thất bại");
            }
            out.flush();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
