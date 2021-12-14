package Client.BUS;

import Client.WorkerClient;
import Shares.DTO.NguoiDungDTO;
import Shares.ServerConfig;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class BUS {

    private static Socket socket;
    private static BufferedWriter out;
    private static BufferedReader in;
    public static NguoiDungDTO user;
    public static ArrayList<NguoiDungDTO> users;
    public static ArrayList<NguoiDungDTO> userTmp;
    public static NguoiDungDTO user2;

    public static String cauHoi;
    public static ArrayList<String> dsDapAn;
    public static String dapAnUser2;

    public static void connect() throws IOException {
        if (socket == null) {
            socket = new Socket(ServerConfig.SERVER, ServerConfig.PORT);
            out = new BufferedWriter(new OutputStreamWriter(BUS.socket.getOutputStream()));
            in = new BufferedReader(new InputStreamReader(BUS.socket.getInputStream()));
            Executors.newFixedThreadPool(1).execute(new WorkerClient((socket)));
            System.out.println("Client connected");
            users = new ArrayList<>();
        }
    }

    public static boolean continute() {
        try {
            while (true) {
                TimeUnit.MILLISECONDS.sleep(500);
                if (WorkerClient.isContinue) {
                    // System.out.println("Break while true is BUS");
                    break;
                }
                System.out.println("waitting...");
            }
            WorkerClient.isContinue = false;
        } catch (InterruptedException ex) {
        }
        return true;
    }

    public static void writeLine(String str) throws IOException {
        out.write(str.trim() + "\n");
    }

    public static void flush() throws IOException {
        out.flush();
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
