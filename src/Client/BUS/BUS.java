package Client.BUS;

import Shares.DTO.NguoiDungDTO;
import Shares.ServerConfig;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BUS {

    public static Socket socket;
    public static BufferedWriter out;
    public static BufferedReader in;
    public static NguoiDungDTO user;
    public static ExecutorService executor = Executors.newFixedThreadPool(10);

    public static void connect() throws IOException {
        if (socket == null) {
            socket = new Socket(ServerConfig.SERVER, ServerConfig.PORT);
            out = new BufferedWriter(new OutputStreamWriter(BUS.getOutputStream()));
            in = new BufferedReader(new InputStreamReader(BUS.getInputStream()));
            System.out.println("Client connected");
        }
    }

    public static void execute(Runnable runnable) {
        executor.execute(runnable);
    }

    public static void writeLine(String str) throws IOException {
        out.write(str.trim() + "\n");
    }

    public static void shutdown() throws IOException {
        executor.shutdown();
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
