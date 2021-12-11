package Client.BUS;

import Shares.DTO.NguoiDungDTO;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class BUS {

    public static BufferedWriter out;
    public static Socket socket;
    public static BufferedReader in;
    public static NguoiDungDTO user;

    public static void writeLine(String str) throws IOException {
        out.write(str.trim() + "\n");
    }

    public static String readLine() throws IOException {
        return in.readLine();
    }

    public static int readLineInt() throws IOException {
        return Integer.parseInt(in.readLine());
    }

    public static void flush() throws IOException {
        out.flush();
    }

    public static OutputStream getOutputStream() throws IOException {
        return socket.getOutputStream();
    }

    public static InputStream getInputStream() throws IOException {
        return socket.getInputStream();
    }

    public static String getMd5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}